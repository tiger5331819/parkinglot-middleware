package com.parkinglotmiddleware.configuration.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.CommonResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suhuyuan
 */
@RestControllerAdvice(basePackages = "com.parkinglotmiddleware.api.dubbo.service.web")
public class CustomResponseAdvice extends CommonResponseBodyAdvice {
}
