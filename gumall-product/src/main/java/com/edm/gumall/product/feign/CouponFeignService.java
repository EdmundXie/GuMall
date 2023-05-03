package com.edm.gumall.product.feign;

import com.edm.common.to.SpuBoundTo;
import com.edm.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Projectname: gumall
 * @Filename: CouponFeignService
 * @Author: EdmundXie
 * @Data:2023/5/2 12:10
 * @Email: 609031809@qq.com
 * @Description:
 */
@FeignClient("gumall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);
}
