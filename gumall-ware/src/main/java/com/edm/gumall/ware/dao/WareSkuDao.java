package com.edm.gumall.ware.dao;

import com.edm.gumall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 23:05:08
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
