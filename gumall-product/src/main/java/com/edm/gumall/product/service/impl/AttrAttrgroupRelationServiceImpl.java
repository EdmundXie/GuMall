package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.service.AttrGroupService;
import com.edm.gumall.product.vo.AttrGroupRelationVo;
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

import com.edm.gumall.product.dao.AttrAttrgroupRelationDao;
import com.edm.gumall.product.entity.AttrAttrgroupRelationEntity;
import com.edm.gumall.product.service.AttrAttrgroupRelationService;

import javax.annotation.Resource;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Resource
    private AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Long> getAttrIdsByGroupId(Long attrgroupId) {
        List<AttrEntity> attrs = new ArrayList<>();
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(attrgroupId!=null,AttrAttrgroupRelationEntity::getAttrGroupId,attrgroupId);
        List<AttrAttrgroupRelationEntity> relationEntityList = this.list(wrapper);

        return relationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
    }

    @Override
    public void deleteBatch(List<AttrAttrgroupRelationEntity> relationEntities) {
        LambdaQueryWrapper<AttrAttrgroupRelationEntity> wrapper = new LambdaQueryWrapper<>();
        for(AttrAttrgroupRelationEntity relation:relationEntities){
            Long attrId = relation.getAttrId();
            Long attrGroupId = relation.getAttrGroupId();
            wrapper.eq(attrId!=null,AttrAttrgroupRelationEntity::getAttrId,attrId)
                    .eq(attrGroupId!=null,AttrAttrgroupRelationEntity::getAttrGroupId,attrGroupId)
                    .or();
        }
        this.remove(wrapper);
    }

    @Override
    public void saveBatch(List<AttrGroupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> collect = relationVos.stream().map((item) -> {
            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relation);
            return relation;
        }).collect(Collectors.toList());

        this.saveBatch(collect);
    }
}