package com.edm.gumall.ware.vo;

import com.edm.gumall.ware.entity.PurchaseDetailEntity;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Projectname: gumall
 * @Filename: PurchaseDetailVo
 * @Author: EdmundXie
 * @Data:2023/5/28 16:23
 * @Email: 609031809@qq.com
 * @Description:
 */

//采购项目的Vo
@Data
public class PurchaseDetailVo{
    //items: [{itemId:1,status:4,reason:""}]
    private Long itemId;
    private Integer status;
    private String reason;
}
