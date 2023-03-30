package com.edm.gumall.coupon.dao;

import com.edm.gumall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:14:48
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
