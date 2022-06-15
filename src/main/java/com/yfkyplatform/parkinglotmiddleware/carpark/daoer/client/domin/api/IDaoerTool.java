package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api;

import reactor.core.publisher.Mono;


/**
 * 工具接口
 *
 * @author Suhuyuan
 */
public interface IDaoerTool {
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
