package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model;

import cn.hutool.core.util.StrUtil;
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
    /**
     * 车牌号
     */
    private String carNo;

    public LifangBase(String uri) {
        this.uri = uri;
    }

    public LifangBase(String uri, String carNo) {
        if (!StrUtil.isBlank(carNo)) {
            this.carNo = carNo;
        }
        this.uri = uri;
    }

    public void setCarNo(String carNo) {
        if (!StrUtil.isBlank(carNo)) {
            this.carNo = carNo;
        }
    }
}
