package com.parkinglotmiddleware.carpark.jieshun.ability;

import com.parkinglotmiddleware.carpark.jieshun.client.domin.api.IJieShunTool;
import com.parkinglotmiddleware.domain.manager.container.service.ability.tool.IToolAblitity;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class JieShunToolAbility implements IToolAblitity {

    private final IJieShunTool api;

    public JieShunToolAbility(IJieShunTool daoerClient) {
        api = daoerClient;
    }

    /**
     * 获取Token
     *
     * @return
     */
    @Override
    public String getToken() {
        return api.getToken();
    }

    /**
     * 获取图片
     *
     * @param imgPath 图片路径
     * @return
     */
    @Override
    public byte[] getImage(String imgPath) {
        return api.getImage(imgPath).block();
    }
}
