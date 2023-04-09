package com.edm.common.exception;

/**
 * @Projectname: gumall
 * @Filename: BizCodeEnum
 * @Author: EdmundXie
 * @Data:2023/4/9 12:53
 * @Email: 609031809@qq.com
 * @Description:
 */
public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"unknown exception"),
    INVALID_EXCEPTION(10001,"parameters invalid");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    BizCodeEnum(int code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
