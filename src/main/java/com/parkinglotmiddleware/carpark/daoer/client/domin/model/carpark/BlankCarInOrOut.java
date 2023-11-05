package com.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;

import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 无牌车入场
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarInOrOut extends DaoerBase {
    private String openId;
    private int scanType;
    private String channelId;

    public void setOpenId(String openId) {
        if(!StrUtil.isBlank(openId)) {
            this.openId = openId;
        }
    }

    public void setChannelId(String channelId) {
        if(!StrUtil.isBlank(channelId)) {
            this.channelId = channelId;
        }
    }
}
