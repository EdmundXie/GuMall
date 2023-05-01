package com.edm.gumall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.SpuImagesDao;
import com.edm.gumall.product.entity.SpuImagesEntity;
import com.edm.gumall.product.service.SpuImagesService;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if(!images.isEmpty()){
            List<SpuImagesEntity> collect = images.stream().map((image) -> {
                SpuImagesEntity spuImages = new SpuImagesEntity();
                spuImages.setSpuId(id);
                spuImages.setImgUrl(image);
                return spuImages;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }
}