package com.edm.gumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.vo.AttrRespVo;
import com.edm.gumall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catlogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttrVo(AttrVo attr);

    PageUtils getAttrPageWithoutThese(Map<String, Object> params,List<Long> ids);
}

