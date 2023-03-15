package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.api.IHongmenTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class HongmenToolAbility implements IToolAblitity {

    private final IHongmenTool api;

    public HongmenToolAbility(IHongmenTool daoerClient) {
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
