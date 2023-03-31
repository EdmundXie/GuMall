package com.edm.gumall.member.feign;

import com.edm.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Projectname: gumall
 * @Filename: CouponFeignService
 * @Author: EdmundXie
 * @Data:2023/3/31 13:20
 * @Email: 609031809@qq.com
 * @Description:
 */
@FeignClient("gumall-coupon")
public interface CouponFeignService {
    @RequestMapping("/coupon/coupon/member/list")
    public R memberCoupons();
}
