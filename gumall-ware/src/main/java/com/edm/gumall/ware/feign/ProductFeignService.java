package com.edm.gumall.ware.feign;

import com.edm.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Projectname: gumall
 * @Filename: ProductFeignService
 * @Author: EdmundXie
 * @Data:2023/5/29 12:20
 * @Email: 609031809@qq.com
 * @Description:
 */
@FeignClient("gumall-product")
public interface ProductFeignService {
    @RequestMapping("product/skuinfo/info/{skuId}")
    R info(@PathVariable("skuId") Long skuId);
}
