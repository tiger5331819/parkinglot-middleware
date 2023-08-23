package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.tool.IToolAblitity;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class LifangToolAbility implements IToolAblitity {

    private final ILifangTool api;

    public LifangToolAbility(ILifangTool daoerClient) {
        api = daoerClient;
    }

    /**
     * 获取Token
     *
     * @return
     */
    @Override
    public String getToken() {
        return api.getSecret();
    }

    /**
     * 获取图片
     *
     * @param imgPath 图片路径
     * @return
     */
    @Override
    public byte[] getImage(String imgPath) {
        throw new UnsupportedOperationException();
    }
}
