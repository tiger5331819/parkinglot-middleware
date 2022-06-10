package com.yfkyplatform.parkinglot.carpark.daoer.ability;

import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.tool.IToolAblitity;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class DaoerToolAbility implements IToolAblitity {

    private IDaoerTool api;

    public DaoerToolAbility(IDaoerTool daoerClient){
        api=daoerClient;
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
