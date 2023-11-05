package com.parkinglotmiddleware.configuration.web;

import cn.hutool.core.util.StrUtil;
import com.yfkyframework.common.mvc.advice.commonresponsebody.CommonResponse;
import com.yfkyframework.common.mvc.advice.globalexceptionhandler.GlobalExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suhuyuan
 */
@RestControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandlerAdvice extends GlobalExceptionHandlerAdvice {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    @Override
    public Object hanleException(Exception e) {
        log.error("【统一异常捕获】{}", e.getMessage(), e);
        return (new CommonResponse()).setCode(1000000).setMsg(e.getMessage());
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public Object handleBindException(Exception e) {
        FieldError fieldError = null;
        if (e instanceof BindException) {
            fieldError = (FieldError) ((BindException) e).getAllErrors().get(0);
        } else if (e instanceof MethodArgumentNotValidException) {
            fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
        }

        String errMsg = StrUtil.concat(true, fieldError.getDefaultMessage());
        return (new CommonResponse()).setCode(1000003).setMsg(errMsg);
    }
}
