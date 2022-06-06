package com.yfkyplatform.parkinglot.carpark.daoer.controller.tools.request;

import lombok.Data;

/**
 * HttpApi代理视图类
 *
 * @author Suhuyuan
 */
@Data
public class ViewHttpApiProxy {
    private String method;
    private String token;
    private String url;
    private String data;
}
