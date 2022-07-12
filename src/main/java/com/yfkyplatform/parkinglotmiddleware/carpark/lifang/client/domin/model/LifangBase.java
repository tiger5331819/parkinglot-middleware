package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model;

import lombok.Getter;

/**
 * 道尔基础请求类
 *
 * @author Suhuyuan
 */
@Getter
public class LifangBase {
    /**
     * API URI
     */
    private final String uri;

    public LifangBase(String uri) {
        this.uri = uri;
    }
}
