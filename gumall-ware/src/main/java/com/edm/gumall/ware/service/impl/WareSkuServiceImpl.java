package com.edm.gumall.ware.service.impl;

import com.alibaba.nacos.client.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.common.utils.Constant;
import com.edm.common.utils.R;
import com.edm.gumall.ware.feign.ProductFeignService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.ware.dao.WareSkuDao;
import com.edm.gumall.ware.entity.WareSkuEntity;
import com.edm.gumall.ware.service.WareSkuService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Resource
    ProductFeignService productFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        String skuId = (String) params.get("skuId");
        String wareId = (String) params.get("wareId");

        wrapper.eq(!StringUtils.isEmpty(skuId),WareSkuEntity::getSkuId,skuId);
        wrapper.eq(!StringUtils.isEmpty(wareId),WareSkuEntity::getWareId,wareId);

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        LambdaQueryWrapper<WareSkuEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(skuId!=null,WareSkuEntity::getSkuId,skuId);
        wrapper.eq(wareId!=null,WareSkuEntity::getWareId,wareId);
        WareSkuEntity wareSku = this.getOne(wrapper);


        if (wareSku==null){
            wareSku = new WareSkuEntity();
            wareSku.setSkuId(skuId);
            wareSku.setStock(skuNum);
            wareSku.setStockLocked(0);

            //远程查询skuName
            try {
                R info = productFeignService.info(skuId);
                if(info.getCode()==0) {
                    Map<String,Object> skuInfo = (Map<String, Object>) info.get("skuInfo");
                    wareSku.setSkuName((String) skuInfo.get("skuName"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else {
            wareSku.setStock(skuNum+wareSku.getStock());
        }
        wareSku.setWareId(wareId);

        this.saveOrUpdate(wareSku);
    }
}