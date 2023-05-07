package com.edm.common.constant;

/**
 * @Projectname: gumall
 * @Filename: WateConstant
 * @Author: EdmundXie
 * @Data:2023/5/6 16:42
 * @Email: 609031809@qq.com
 * @Description:
 */
public class WareConstant {
    public enum PurchaseStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVED(2,"已领取"),
        FINISHED(3,"已完成"),
        ERROR(4,"有异常");

        private int code;
        private String message;

        PurchaseStatusEnum(int code,String message){
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

    public enum PurchaseDetailStatusEnum{
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        PROCUREMENT(2,"正在采购"),
        FINISHED(3,"已完成"),
        ERROR(4,"采购失败");

        private int code;
        private String message;

        PurchaseDetailStatusEnum(int code,String message){
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
