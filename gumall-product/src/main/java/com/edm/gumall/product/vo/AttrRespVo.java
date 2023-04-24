package com.edm.gumall.product.vo;

import lombok.Data;

/**
 * @Projectname: gumall
 * @Filename: AttrRespVo
 * @Author: EdmundXie
 * @Data:2023/4/23 15:49
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class AttrRespVo extends AttrVo{
    /*
            "catelogName": "手机/数码/手机", //所属分类名字
			"groupName": "主体", //所属分组名字
     */
    private String catelogName;

    private String groupName;

    /**
     * 分组id
     */
    private Long attrGroupId;

    private Long[] catelogPath;
}
