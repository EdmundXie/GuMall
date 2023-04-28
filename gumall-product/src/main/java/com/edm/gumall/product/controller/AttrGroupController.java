package com.edm.gumall.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.edm.gumall.product.entity.AttrAttrgroupRelationEntity;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.service.AttrAttrgroupRelationService;
import com.edm.gumall.product.service.AttrService;
import com.edm.gumall.product.service.CategoryService;
import com.edm.gumall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edm.gumall.product.entity.AttrGroupEntity;
import com.edm.gumall.product.service.AttrGroupService;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 21:06:06
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Resource
    private AttrGroupService attrGroupService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    private AttrService attrService;

    /**
     * 添加属性与分组关联关系
     * /product/attrgroup/attr/relation
     */
    @PostMapping("/attr/relation")
    public R addAttrAttrGroupRelation(@RequestBody List<AttrGroupRelationVo> relationVos){
        attrAttrgroupRelationService.saveBatch(relationVos);
        return R.ok();
    }

    /**
     * 获取属性分组没有关联的其他属性
     * /product/attrgroup/{attrgroupId}/noattr/relation
     */
    @RequestMapping("/{attrgroupId}/noattr/relation")
    public R getAttrWithoutRelation(@RequestParam Map<String, Object> params, @PathVariable Long attrgroupId){
        PageUtils page = attrGroupService.queryPageWithoutRelation(params,attrgroupId);
        return R.ok().put("page",page);
    }

    /**
     * 获取属性分组的关联的所有属性
     * /product/attrgroup/{attrgroupId}/attr/relation
     */
    @RequestMapping("/{attrgroupId}/attr/relation")
    public R getAttr(@PathVariable Long attrgroupId){
        List<Long> ids= attrAttrgroupRelationService.getAttrIdsByGroupId(attrgroupId);
        List<AttrEntity> attrs = new ArrayList<>();
        if(!ids.isEmpty()){
            attrs = (List<AttrEntity>) attrService.listByIds(ids);
        }
        return R.ok().put("data",attrs);
    }

    /**
     * 删除属性与分组的关联关系
     * /product/attrgroup/attr/relation/delete
     */
    @RequestMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){

        attrGroupService.deleteRelation(vos);
        return R.ok("success");
    }

    /**
     * 列表 获取一个分类下的所有属性分组
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable Long catelogId){
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catlogId = attrGroup.getCatelogId();
        Long[] path = categoryService.getPath(catlogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
