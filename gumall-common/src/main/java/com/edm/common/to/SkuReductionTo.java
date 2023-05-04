package com.edm.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Projectname: gumall
 * @Filename: SkuReduction
 * @Author: EdmundXie
 * @Data:2023/5/3 12:51
 * @Email: 609031809@qq.com
 * @Description:
 */
@Data
public class SkuReductionTo {
    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrice;
}
