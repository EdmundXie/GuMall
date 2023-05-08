package com.edm.gumall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.common.constant.WareConstant;
import com.edm.gumall.ware.entity.PurchaseDetailEntity;
import com.edm.gumall.ware.service.PurchaseDetailService;
import com.edm.gumall.ware.vo.MergeVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.ware.dao.PurchaseDao;
import com.edm.gumall.ware.entity.PurchaseEntity;
import com.edm.gumall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Resource
    PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivePage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new LambdaQueryWrapper<PurchaseEntity>().eq(PurchaseEntity::getStatus,0).or().eq(PurchaseEntity::getStatus,1)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void merge(MergeVo mergeVo) {
        Long purchaseId = mergeVo.getPurchaseId();
        if(purchaseId==null){
            //1. 如果没有有Id
            //1.1 在purchase表中创建采购单
            PurchaseEntity purchase = new PurchaseEntity();
            purchase.setStatus(WareConstant.PurchaseStatusEnum.CREATED.getCode());
            this.save(purchase);
            purchaseId = purchase.getId();
        }
        //2. 在purchaseDetail中更新采购单id
        List<Long> items = mergeVo.getItems();
        purchaseDetailService.updatePurchaseIdAndStatusToAssigned(items,purchaseId);

        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setId(purchaseId);
        purchase.setUpdateTime(new Date());
        this.updateById(purchase);
    }

    @Transactional
    @Override
    public void received(List<Long> idList) {
        //1. 确认当前采购单是已分配状态

        List<PurchaseEntity> collect = idList.stream().map(this::getById).filter(item -> {
            return item.getStatus() == WareConstant.PurchaseStatusEnum.ASSIGNED.getCode();
        }).peek(item-> item.setStatus(WareConstant.PurchaseStatusEnum.RECEIVED.getCode())).collect(Collectors.toList());

        //TODO 2. 采购单的采购员是自己

        //3. 改变采购单的状态为正在采购中：WareConstant.PurchaseStatusEnum.RECEIVED
        this.updateBatchById(collect);

        //4. 改变采购需求的状态为已分配：WareConstant.PurchaseDetailStatusEnum.ASSIGNED
        List<PurchaseDetailEntity> list = new ArrayList<>();
        collect.forEach(item->{
            List<PurchaseDetailEntity> detailListByPurchaseId = purchaseDetailService.getDetailListByPurchaseId(item.getId());
            detailListByPurchaseId.forEach(PurchaseDetail->{
                PurchaseDetail.setStatus(WareConstant.PurchaseDetailStatusEnum.PROCUREMENT.getCode());
            });
            list.addAll(detailListByPurchaseId);
        });
        purchaseDetailService.updateBatchById(list);
    }
}