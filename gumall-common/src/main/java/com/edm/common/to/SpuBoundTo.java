package com.edm.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Projectname: gumall
 * @Filename: SpuBoundTo
 * @Author: EdmundXie
 * @Data:2023/5/2 12:14
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
