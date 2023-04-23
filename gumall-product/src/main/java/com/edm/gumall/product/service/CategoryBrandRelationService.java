package com.edm.gumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.product.entity.CategoryBrandRelationEntity;

import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrandName(Long brandId, String name);

    void updateCatlogName(Long catId, String name);
}

