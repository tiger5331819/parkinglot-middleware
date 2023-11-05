package com.parkinglotmiddleware.carpark.jieshun.client.domin.model.carpark;

import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 无牌车入场
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarInOrOut extends JieShunBase {
    private String openId;
    private int scanType;
    private String channelId;

    public void setOpenId(String openId) {
        if (!StrUtil.isBlank(openId)) {
            this.openId = openId;
        }
    }

    public void setChannelId(String channelId) {
        if (!StrUtil.isBlank(channelId)) {
            this.channelId = channelId;
        }
    }
}
