package com.edm.gumall.product.exception;

import com.edm.common.exception.BizCodeEnum;
import com.edm.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Projectname: gumall
 * @Filename: ProductExceptionHandler
 * @Author: EdmundXie
 * @Data:2023/4/9 13:02
 * @Email: 609031809@qq.com
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class ProductExceptionHandler {

    /*
     * 处理数据校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleInvalidException(MethodArgumentNotValidException e){
//        log.error("异常类型{},异常信息：{}",e.getClass(),e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> resultMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((item)->{
            String code = item.getCode();
            String field = item.getField();
            resultMap.put(code,field);
        });
        return R.error(BizCodeEnum.INVALID_EXCEPTION.getCode(), BizCodeEnum.INVALID_EXCEPTION.getMsg()).put("data",resultMap);
    }

    /*
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public R handleUnknownException(Exception e){
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
