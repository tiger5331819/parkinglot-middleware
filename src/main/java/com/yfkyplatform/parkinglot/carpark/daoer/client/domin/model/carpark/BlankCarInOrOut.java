package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.carpark;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 无牌车入场
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class BlankCarInOrOut extends DaoerBase {
    private String openId;
    private int scanType;
    private String channelId;

    public BlankCarInOrOut(String uri) {
        super(uri);
    }

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
