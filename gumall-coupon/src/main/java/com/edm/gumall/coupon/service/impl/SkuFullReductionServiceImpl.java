package com.edm.gumall.coupon.service.impl;

import com.edm.common.to.MemberPrice;
import com.edm.common.to.SkuReductionTo;
import com.edm.gumall.coupon.entity.MemberPriceEntity;
import com.edm.gumall.coupon.entity.SkuLadderEntity;
import com.edm.gumall.coupon.service.MemberPriceService;
import com.edm.gumall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.coupon.dao.SkuFullReductionDao;
import com.edm.gumall.coupon.entity.SkuFullReductionEntity;
import com.edm.gumall.coupon.service.SkuFullReductionService;

import javax.annotation.Resource;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Resource
    SkuLadderService skuLadderService;

    @Resource
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        //1. sku的优惠
        SkuLadderEntity skuLadder = new SkuLadderEntity();
        skuLadder.setSkuId(skuReductionTo.getSkuId());
        skuLadder.setDiscount(skuReductionTo.getDiscount());
        skuLadder.setFullCount(skuReductionTo.getFullCount());
        skuLadder.setAddOther(skuReductionTo.getCountStatus());
        skuLadderService.save(skuLadder);

        //2. 满减
        SkuFullReductionEntity skuFullReduction = new SkuFullReductionEntity();
        BeanUtils.copyProperties(skuReductionTo,skuFullReduction);
        this.save(skuFullReduction);

        //3. 会员价
        List<MemberPrice> memberPriceList = new ArrayList<>();

        List<MemberPriceEntity> collect = memberPriceList.stream().map((item) -> {
            MemberPriceEntity memberPrice = new MemberPriceEntity();
            memberPrice.setSkuId(skuReductionTo.getSkuId());
            memberPrice.setMemberLevelId(item.getId());
            memberPrice.setMemberLevelName(item.getName());
            if(item.getPrice()!=null){
                memberPrice.setMemberPrice(item.getPrice());
            }
            memberPrice.setAddOther(1);
            return memberPrice;
        }).collect(Collectors.toList());

        memberPriceService.saveBatch(collect);
    }
}