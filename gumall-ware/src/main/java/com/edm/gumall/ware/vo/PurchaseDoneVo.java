package com.edm.gumall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Projectname: gumall
 * @Filename: PurchaseDoneVo
 * @Author: EdmundXie
 * @Data:2023/5/28 16:20
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id;

    private List<PurchaseDetailVo> items;
}
