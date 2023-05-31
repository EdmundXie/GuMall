package com.edm.gumall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.edm.gumall.product.entity.ProductAttrValueEntity;
import com.edm.gumall.product.service.ProductAttrValueService;
import com.edm.gumall.product.vo.AttrRespVo;
import com.edm.gumall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.service.AttrService;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.R;

import javax.annotation.Resource;


/**
 * 商品属性
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 21:06:06
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Resource
    private AttrService attrService;

    @Resource
    ProductAttrValueService productAttrValueService;

    /**
     * 获取spu规格
     * /product/attr/base/listforspu/{spuId}
     *
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrListForSpu(@PathVariable Long spuId){
        List<ProductAttrValueEntity> data = productAttrValueService.getAttrList(spuId);
        return R.ok().put("data",data);
    }


    /**
     * 规格参数/销售属性列表
     */
    @RequestMapping("/{attrType}/list/{catlogId}")
    //@RequiresPermissions("product:attr:list")
    public R baseList(@RequestParam Map<String, Object> params,@PathVariable String attrType,@PathVariable Long catlogId){
        PageUtils page = attrService.queryBaseAttrPage(params,catlogId, attrType);

        return R.ok().put("page", page);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		AttrRespVo attrRespVo = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttrVo(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttrVo(attr);

        return R.ok();
    }

    /**
     * 修改spu
     */
    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttr(@PathVariable Long spuId,@RequestBody List<ProductAttrValueEntity> entities){
        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
