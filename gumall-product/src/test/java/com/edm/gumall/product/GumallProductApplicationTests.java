package com.edm.gumall.product;

import com.edm.gumall.product.entity.BrandEntity;
import com.edm.gumall.product.service.BrandService;
import com.edm.gumall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;


@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GumallProductApplicationTests {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Test
    public void categoryPathTest(){
        Long[] path = categoryService.getPath(225L);
        log.info("完整路径：{}", Arrays.asList(path));
    }
    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();

        brandEntity.setName("华为");
        brandService.save(brandEntity);
        System.out.println("保存成功");
    }

}
