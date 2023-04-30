package com.edm.gumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.product.entity.AttrEntity;
import com.edm.gumall.product.entity.AttrGroupEntity;
import com.edm.gumall.product.vo.AttrGroupRelationVo;
import com.edm.gumall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {
    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils queryPageWithoutRelation(Map<String, Object> params, Long attrgroupId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    List<AttrEntity> getAttrsWithAttrGroupId(Long attrgroupId);
}

