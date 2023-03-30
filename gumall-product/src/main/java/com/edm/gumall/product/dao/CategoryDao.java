package com.edm.gumall.product.dao;

import com.edm.gumall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
