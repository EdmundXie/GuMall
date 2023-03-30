package com.edm.gumall.order.dao;

import com.edm.gumall.order.entity.RefundInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:57:45
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {
	
}
