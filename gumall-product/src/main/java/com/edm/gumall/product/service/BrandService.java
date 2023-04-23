package com.edm.gumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

