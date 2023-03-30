package com.edm.gumall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:57:46
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

