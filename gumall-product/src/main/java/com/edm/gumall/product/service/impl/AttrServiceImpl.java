package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.edm.common.constant.ProductConstant;
import com.edm.gumall.product.entity.AttrAttrgroupRelationEntity;
import com.edm.gumall.product.entity.AttrGroupEntity;
import com.edm.gumall.product.entity.CategoryEntity;
import com.edm.gumall.product.service.AttrAttrgroupRelationService;
import com.edm.gumall.product.service.AttrGroupService;
import com.edm.gumall.product.service.CategoryService;
import com.edm.gumall.product.vo.AttrRespVo;
import com.edm.gumall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.AttrDao;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    CategoryService categoryService;

    @Resource
    AttrGroupService attrGroupService;

    @Resource
    AttrAttrgroupRelationService relationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttrVo(AttrVo attr) {

        //1. 保存基本数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);

        //2. 保存关联关系
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrGroupId(attr.getAttrGroupId());
            relation.setAttrId(attrEntity.getAttrId());
            relationService.save(relation);
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catlogId, String attrType) {
        //Page中有关键字搜索
        String key = (String) params.get("key");
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();

        if(attrType!=null){
            wrapper.eq(AttrEntity::getAttrType,"base".equalsIgnoreCase(attrType)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        }
        //AttrGroupId与关键字相同或AttrGroupName与关键字相似where name like %key%
        if(!StringUtils.isEmpty(key))
        {
            wrapper.and((item)->{
                item.eq(AttrEntity::getAttrId,key).or().like(AttrEntity::getAttrName,key);
            });
        }

        //有catelogId的数据
        if(catlogId!=0){
            //建立查询
            wrapper.eq(AttrEntity::getCatelogId,catlogId);
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        //获得所属分类名字catelogName以及所属分组名字attrGroupName
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> newRecords = records.stream().map((item) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            //拷贝基本属性
            BeanUtils.copyProperties(item, attrRespVo);

            //1. 得到category对象
            if(item.getCatelogId()!=null){
                CategoryEntity category = categoryService.getById(item.getCatelogId());
                attrRespVo.setCatelogName(category.getName());
            }

            if("base".equalsIgnoreCase(attrType)){
                //2.1 先得到AttrAttrgroupRelationEntity对象
                LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
                relationEntityLambdaQueryWrapper.eq(item.getAttrId()!=null,AttrAttrgroupRelationEntity::getAttrId,item.getAttrId());
                AttrAttrgroupRelationEntity relationEntity = relationService.getOne(relationEntityLambdaQueryWrapper);

                //2.2 根据AttrAttrgroupRelationEntity中的attrGroupId查attrGroupName
                if(relationEntity!=null&&relationEntity.getAttrGroupId()!=null){
                    AttrGroupEntity attrGroup = attrGroupService.getById(relationEntity.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroup.getAttrGroupName());
                }
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(newRecords);
        return pageUtils;
    }

    /*
		"valueType": 1,

		"attrGroupId": 1, //分组id

		"catelogPath": [2, 34, 225] //分类完整路径
     */
    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);
        //1.1 先得到AttrAttrgroupRelationEntity对象
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> relationEntityLambdaQueryWrapper = new LambdaQueryWrapper<>();
        relationEntityLambdaQueryWrapper.eq(attrId!=null,AttrAttrgroupRelationEntity::getAttrId,attrId);
        AttrAttrgroupRelationEntity relationEntity = relationService.getOne(relationEntityLambdaQueryWrapper);

        //1.2 根据AttrAttrgroupRelationEntity中的attrGroupId查attrGroupName
        if(relationEntity!=null&&relationEntity.getAttrGroupId()!=null){
            AttrGroupEntity attrGroup = attrGroupService.getById(relationEntity.getAttrGroupId());

            attrRespVo.setAttrGroupId(relationEntity.getAttrGroupId());
        }

        Long catelogId = attrRespVo.getCatelogId();
        if(catelogId!=null){
            attrRespVo.setCatelogPath(categoryService.getPath(catelogId));
            CategoryEntity category = categoryService.getById(catelogId);
            if(category!=null){
                attrRespVo.setAttrName(category.getName());
            }
        }
        return attrRespVo;
    }

    @Transactional
    @Override
    public void updateAttrVo(AttrVo attr) {
        //1. 更新基本数据
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);

        //2. 更新关联关系
        AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();

//        //2.1 先查有没有数据
//        LambdaQueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(attrEntity.getAttrId()!=null,AttrAttrgroupRelationEntity::getAttrId,attrEntity.getAttrId());
//        AttrAttrgroupRelationEntity relationEntity = relationService.getOne(queryWrapper);
//        if(relationEntity==null){
//            relationService.save(relationEntity);
//            return;
//        }

        LambdaUpdateWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(attrEntity.getAttrId()!=null,AttrAttrgroupRelationEntity::getAttrId,attrEntity.getAttrId());

        if(attr.getAttrGroupId()!=null){
            relation.setAttrGroupId(attr.getAttrGroupId());
        }
        if(attr.getAttrId()!=null){
            relation.setAttrId(attr.getAttrId());
        }
        relationService.saveOrUpdate(relation,wrapper);
    }

}