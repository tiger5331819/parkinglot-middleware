package com.parkinglotmiddleware.carpark.jieshun.client.domin.model.carpark;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 通道信息
 *
 * @author Suhuyuan
 */
@Data
public class Channel extends JieShunBase {
    /**
     * 通道ID
     */
    private String channelId;
    /**
     * 1开启 2 关闭
     */
    private int channelIdStatus;

    public void setChannelId(String channelId) {
        if (!StrUtil.isBlank(channelId)) {
            this.channelId = channelId;
        }
    }

    public void setChannelIdStatus(int channelIdStatus) {
        if (ObjectUtil.isNotNull(channelIdStatus)) {
            this.channelIdStatus = channelIdStatus;
        }
    }
}
