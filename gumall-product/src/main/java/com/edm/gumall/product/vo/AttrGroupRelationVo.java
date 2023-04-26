package com.edm.gumall.product.vo;

import lombok.Data;

/**
 * @Projectname: gumall
 * @Filename: AttrGroupRelationVo
 * @Author: EdmundXie
 * @Data:2023/4/26 13:50
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class AttrGroupRelationVo {
    /**
     * 分组id
     */
    private Long attrGroupId;

    /**
     * 属性id
     */
    private Long attrId;
}
