package com.edm.gumall.order.dao;

import com.edm.gumall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:57:46
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
