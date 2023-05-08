package com.edm.gumall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.ware.entity.PurchaseEntity;
import com.edm.gumall.ware.vo.MergeVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 23:05:08
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryUnreceivePage(Map<String, Object> params);

    void merge(MergeVo mergeVo);

    void received(List<Long> idList);
}

