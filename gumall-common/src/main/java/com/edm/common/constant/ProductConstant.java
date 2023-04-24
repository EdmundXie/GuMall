package com.edm.common.constant;

/**
 * @Projectname: gumall
 * @Filename: ProductConstant
 * @Author: EdmundXie
 * @Data:2023/4/24 15:05
 * @Email: 609031809@qq.com
 * @Description:
 */
public class ProductConstant {
    public enum AttrEnum{
        ATTR_TYPE_BASE(1,"规格参数"),
        ATTR_TYPE_SALE(0,"销售属性");

        private int code;
        private String message;

        AttrEnum(int code,String message){
            this.code=code;
            this.message=message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
}
