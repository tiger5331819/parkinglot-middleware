package com.yfkyplatform.parkinglotmiddleware.configuartion.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.CommonResponse;
import com.yfkyframework.common.mvc.advice.globalexceptionhandler.GlobalExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
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
}
