package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model;

import lombok.Data;

/**
 * 捷顺基础请求类
 *
 * @author Suhuyuan
 */
@Data
public class JieShunBase {


    /**
     * 项目编号
     */
    private String projectCode;

    /**
     * 开放服务编码
     */
    private String abilityCode;

    /**
     * 接口编码
     */
    private String method;

    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 字符集
     */
    private String charset = "utf-8";

    /**
     * 默认json,调用接口编码method以3C.xx开头的接口，需要传form
     */
    private String format;

    /**
     * 具体接口请求参数，如果调接口编号以3C.xx开头的接口，需要添加三个参数cid、v、p到biz_content中 ，并将接口请求参数放入p参数中
     */
    private Object bizContent;
}
