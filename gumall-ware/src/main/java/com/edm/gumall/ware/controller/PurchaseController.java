package com.edm.gumall.ware.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.edm.gumall.ware.vo.MergeVo;
import com.edm.gumall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edm.gumall.ware.entity.PurchaseEntity;
import com.edm.gumall.ware.service.PurchaseService;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.R;



/**
 * 采购信息
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 23:05:08
 */
@RestController
@RequestMapping("ware/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 完成采购单
     * /ware/purchase/received
     * [1,2,3,4]//采购单id
     */
    @PostMapping("/done")
    public R finishPurchase(@RequestBody PurchaseDoneVo purchaseDoneVo){
        purchaseService.finish(purchaseDoneVo);

        return R.ok();
    }

    /**
     * 领取采购单
     * /ware/purchase/received
     * [1,2,3,4]//采购单id
     */
    @PostMapping("/received")
    public R receivePurchase(@RequestBody List<Long> idList){
        purchaseService.received(idList);

        return R.ok();
    }

    /**
     * 合并采购需求 （purchaseId采购单Id没有时，创建新的采购单
     * {
     *   purchaseId: 1, //整单id
     *   items:[1,2,3,4] //合并项集合
     * }
     * /ware/purchase/merge
     */
    @PostMapping("/merge")
    public R mergePurchase(@RequestBody MergeVo mergeVo){

        purchaseService.merge(mergeVo);
        return R.ok();
    }

    /**
     * 查询未领取的采购单
     * /ware/purchase/unreceive/list
     */
    @GetMapping("/unreceive/list")
    public R unreceiveList(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryUnreceivePage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:purchase:info")
    public R info(@PathVariable("id") Long id){
		PurchaseEntity purchase = purchaseService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    public R save(@RequestBody PurchaseEntity purchase){
		purchaseService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public R update(@RequestBody PurchaseEntity purchase){
		purchaseService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
		purchaseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
