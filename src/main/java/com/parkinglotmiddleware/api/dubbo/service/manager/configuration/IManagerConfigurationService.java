package com.parkinglotmiddleware.api.dubbo.service.manager.configuration;

import com.yfkyframework.common.core.exception.ExposerException;

/**
 * @author Suhuyuan
 */
public interface IManagerConfigurationService {
    /**
     * 添加道尔停车场配置文件
     *
     * @param id      停车场Id
     * @param appName 道尔 appName
     * @param parkId  道尔 parkId
     * @param baseUrl 道尔基础访问地址
     * @param imgUrl  道尔图片访问地址
     * @return
     */
    Boolean addDaoerCongfiguration(String id, String appName, String parkId, String baseUrl, String description, String imgUrl) throws ExposerException;
}
