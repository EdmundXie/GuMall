package com.edm.gumall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @Projectname: gumall
 * @Filename: MergeVo
 * @Author: EdmundXie
 * @Data:2023/5/6 16:26
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
