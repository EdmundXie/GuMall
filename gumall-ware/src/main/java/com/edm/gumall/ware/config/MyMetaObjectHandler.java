package com.edm.gumall.ware.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @Projectname: gumall
 * @Filename: MyMetaObjectHandler
 * @Author: EdmundXie
 * @Data:2023/5/6 16:54
 * @Email: 609031809@qq.com
 * @Description:
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime",  new Date());
    }

    //更新时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = this.getFieldValByName("updateTime",metaObject);
        if(Objects.isNull(updateTime)){
            metaObject.setValue("updateTime", new Date());
        }
    }
}
