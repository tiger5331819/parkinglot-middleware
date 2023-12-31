package com.parkinglotmiddleware.carpark.jieshun.client.domin.api;

import reactor.core.publisher.Mono;


/**
 * 工具接口
 *
 * @author Suhuyuan
 */
public interface IJieShunTool {
    /**
     * 获取Token
     *
     * @return
     */
    String getToken();

    /**
     * 获取图片
     *
     * @return
     */
    Mono<byte[]> getImage(String imgPath);

}
