package com.edm.gumall.ware.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.common.constant.WareConstant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.ware.dao.PurchaseDetailDao;
import com.edm.gumall.ware.entity.PurchaseDetailEntity;
import com.edm.gumall.ware.service.PurchaseDetailService;
import org.springframework.transaction.annotation.Transactional;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<PurchaseDetailEntity> wrapper = new LambdaQueryWrapper<>();
        String status = (String) params.get("status");
        String key = (String) params.get("key");
        String wareId = (String) params.get("wareId");

        if(!StringUtils.isEmpty(key)){
            wrapper.and(w->{
                w.eq(PurchaseDetailEntity::getPurchaseId,key).or().eq(PurchaseDetailEntity::getSkuId,key);
            });
        }
        wrapper.eq(!StringUtils.isEmpty(status),PurchaseDetailEntity::getStatus,status);
        wrapper.eq(!StringUtils.isEmpty(wareId),PurchaseDetailEntity::getWareId,wareId);

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updatePurchaseIdAndStatus(List<Long> items, Long purchaseId) {
        List<PurchaseDetailEntity> collect = items.stream().map((item) -> {
            PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();
            purchaseDetail.setId(item);
            purchaseDetail.setPurchaseId(purchaseId);
            purchaseDetail.setStatus(WareConstant.PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetail;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }
}