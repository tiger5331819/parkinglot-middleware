package com.yfkyplatform.parkinglotmiddleware.configuartion.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.CommonResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Suhuyuan
 */
@RestControllerAdvice(basePackages = "com.yfkyplatform.parkinglotmiddleware.api.web")
public class CustomResponseAdvice extends CommonResponseBodyAdvice {
}
