package com.edm.gumall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edm.common.utils.PageUtils;
import com.edm.common.utils.Query;

import com.edm.gumall.product.dao.AttrGroupDao;
import com.edm.gumall.product.entity.AttrGroupEntity;
import com.edm.gumall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        //没有catelogId的数据
        if(catelogId==0){
            return this.queryPage(params);
        }else {
            //建立查询
            LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(AttrGroupEntity::getCatelogId,catelogId);

            //Page中有关键字搜索
            String key = (String) params.get("key");

            //AttrGroupId与关键字相同或AttrGroupName与关键字相似where name like %key%
            if(!StringUtils.isEmpty(key))
            {
                wrapper.and((item)->{
                    item.eq(AttrGroupEntity::getAttrGroupId,key).or().like(AttrGroupEntity::getAttrGroupName,key);
                });
            }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }
    }
}