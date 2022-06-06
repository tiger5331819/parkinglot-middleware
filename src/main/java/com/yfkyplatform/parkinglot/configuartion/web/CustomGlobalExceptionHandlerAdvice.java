package com.yfkyplatform.parkinglot.configuartion.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suhuyuan
 */
@RestControllerAdvice()
public class CustomGlobalExceptionHandlerAdvice {

    /**
     * 返回json
     */
    @ExceptionHandler
    @ResponseBody
    public ErrorResult exceptionHandler2(Exception e) {
        return new ErrorResult(500, e.getMessage());
    }
}
