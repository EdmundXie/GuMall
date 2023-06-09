package com.edm.gumall.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.gumall.product.entity.CategoryBrandRelationEntity;
import com.edm.gumall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.CategoryDao;
import com.edm.gumall.product.entity.CategoryEntity;
import com.edm.gumall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> categories = this.list(null);
        //2、组装成父子的树形结构
        categories.stream().filter(item-> item.getParentCid()==0)
                .map(item->{
                    item.setChildren(getChildren(item,categories));
                    return item;
                })
                .sorted((item1,item2)->{
                    Integer sort1 = item1.getSort()==null?0: item1.getSort();
                    Integer sort2 = item2.getSort()==null?0: item2.getSort();
                    return sort1-sort2;
                }).collect(Collectors.toList());
        return categories;
    }

    /*
     * 根据Id删除菜单，所删除的菜单需要没有子菜单
     */
    @Override
    public void removeMenuByIds(List<Long> ids) {
        //TODO //检查当前删除的菜单是否被别的地方引用

        //1. 检查是否有子菜单

        //2. 没有子菜单，检查是否有父菜单
        //2.1 有父菜单，找到在父菜单里删除本条子菜单

        //3. 没有父菜单直接删除
        //逻辑删除
        this.removeByIds(ids);
    }

    @Override
    public Long[] getPath(Long catlogId) {
        List<Long> path = new ArrayList<>();
        getParent(catlogId,path);
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    public void getParent(Long catelogId, List<Long> path){
        path.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            getParent(byId.getParentCid(),path);
        }
    }

    //递归查找所有Children
    public List<CategoryEntity> getChildren(CategoryEntity parent,List<CategoryEntity> categories){
        return categories.stream()
                .filter(item -> Objects.equals(item.getParentCid(), parent.getCatId()))
                .peek(item-> item.setChildren(getChildren(item,categories)))
                .sorted((item1,item2)->{
                    Integer sort1 = item1.getSort()==null?0: item1.getSort();
                    Integer sort2 = item2.getSort()==null?0: item2.getSort();
                    return sort1-sort2;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        if(!StringUtils.isEmpty(category.getName())){

            //更新categoryBrandRelationService
            categoryBrandRelationService.updateCatlogName(category.getCatId(),category.getName());

            //TODO 其他冗余表的更新
        }
    }
}