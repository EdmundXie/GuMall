package com.edm.gumall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edm.common.utils.PageUtils;
import com.edm.gumall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 17:13:27
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    /*
     * 逻辑删除：
     * 1.在yml的Myatis-Plus中配置
     * 2.配置全局的逻辑删除规则（省略）
     * 3.配置逻辑删除组件的bean（省略）
     * 4.在entity表中的某一属性加上逻辑删除@TableLogic的注解
     */
    void removeMenuByIds(List<Long> ids);

    //找到catlogId的完整路径
    //[父/子/孙子]
    Long[] getPath(Long catlogId);

    void updateCascade(CategoryEntity category);
}

