package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model;

import lombok.Data;

/**
 * 捷顺签名基础请求类
 *
 * @author Suhuyuan
 */
@Data
public class JieShunSignBase extends JieShunBase {

    /**
     * 开放平台分配的应用id
     */
    private String appId;

    /**
     * 签名类型
     */
    private String signType = "md5";

    /**
     * 签名串
     */
    private String sign;
}
