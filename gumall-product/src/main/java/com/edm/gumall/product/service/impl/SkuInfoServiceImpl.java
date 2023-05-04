package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.gumall.product.entity.SpuInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.SkuInfoDao;
import com.edm.gumall.product.entity.SkuInfoEntity;
import com.edm.gumall.product.service.SkuInfoService;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.save(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<SkuInfoEntity> wrapper = new LambdaQueryWrapper<>();
        String key = (String) params.get("key");
        String min = (String) params.get("min");
        String max = (String) params.get("max");
        String brandId = (String) params.get("brandId");
        String catelogId = (String) params.get("catelogId");

        if(!StringUtils.isEmpty(key)){
            wrapper.and(wp -> wp.eq(SkuInfoEntity::getSkuId,key).or().like(SkuInfoEntity::getSkuName,key));
        }

        wrapper.ge(!StringUtils.isEmpty(min),SkuInfoEntity::getPrice,min)
                .le(!StringUtils.isEmpty(max)&&!"0".equalsIgnoreCase(max),SkuInfoEntity::getPrice,max)
                .eq(!StringUtils.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId), SkuInfoEntity::getBrandId,brandId)
                .eq(!StringUtils.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId),SkuInfoEntity::getCatalogId,catelogId);

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }
}