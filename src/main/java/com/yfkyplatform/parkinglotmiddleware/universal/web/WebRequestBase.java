package com.yfkyplatform.parkinglotmiddleware.universal.web;

import lombok.Getter;

/**
 * Web基础请求类
 *
 * @author Suhuyuan
 */
@Getter
public class WebRequestBase {
    /**
     * API URI
     */
    protected String uri;

    public WebRequestBase(String uri) {
        this.uri = uri;
    }
}
