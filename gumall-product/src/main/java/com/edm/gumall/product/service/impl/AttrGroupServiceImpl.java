package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.gumall.product.entity.AttrAttrgroupRelationEntity;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.service.AttrAttrgroupRelationService;
import com.edm.gumall.product.service.AttrService;
import com.edm.gumall.product.vo.AttrGroupRelationVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.AttrGroupDao;
import com.edm.gumall.product.entity.AttrGroupEntity;
import com.edm.gumall.product.service.AttrGroupService;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        //Page中有关键字搜索
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        //AttrGroupId与关键字相同或AttrGroupName与关键字相似where name like %key%
        if(!StringUtils.isEmpty(key))
        {
            wrapper.and((item)->{
                item.eq(AttrGroupEntity::getAttrGroupId,key).or().like(AttrGroupEntity::getAttrGroupName,key);
            });
        }

        //有catelogId的数据
        if(catelogId!=0){
            //建立查询
            wrapper.eq(AttrGroupEntity::getCatelogId,catelogId);
        }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {

        List<AttrAttrgroupRelationEntity> relationEntities = Arrays.stream(vos).map((vo) -> {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(vo,relation);
            return relation;
        }).collect(Collectors.toList());


        attrAttrgroupRelationService.deleteBatch(relationEntities);
    }

    @Override
    public PageUtils queryPageWithoutRelation(Map<String, Object> params, Long attrgroupId) {
        //1. 当前分组只能关联所属分类下的所有属性
        AttrGroupEntity attrGroup = this.getById(attrgroupId);
        Long catelogId = attrGroup.getCatelogId();

        //2. 当前分组只能关联没被别的分组关联的属性
        //2.1 查当前分类下的其他分组
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(catelogId!=null,AttrGroupEntity::getCatelogId,catelogId);
        List<AttrGroupEntity> attrGroups = this.list(wrapper);
        //2.2 查被其他分组关联的所有属性
        Set<Long> idSet = new HashSet<>();
        attrGroups.forEach((item) ->{
            Long attrGroupId = item.getAttrGroupId();
            List<Long> ids = attrAttrgroupRelationService.getAttrIdsByGroupId(attrGroupId);
            idSet.addAll(ids);
        });
        List<Long> ids = new ArrayList<>(idSet);
        //2.3 从当前分类的属性中移除这些属性
        return attrService.getAttrPageWithoutThese(params,ids);
    }
}