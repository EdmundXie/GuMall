package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.gumall.product.entity.CategoryBrandRelationEntity;
import com.edm.gumall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.BrandDao;
import com.edm.gumall.product.entity.BrandEntity;
import com.edm.gumall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    //1. 获取key进行模糊查询
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String key = (String) params.get("key");
        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq(BrandEntity::getBrandId,key).or().like(BrandEntity::getName,key);
        }
        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(BrandEntity brand) {
        //保证冗余的数据实时更新
        this.updateById(brand);
        if(!StringUtils.isEmpty(brand.getName())){
            categoryBrandRelationService.updateBrandName(brand.getBrandId(),brand.getName());

            //TODO 更新其他关联
        }
    }
}