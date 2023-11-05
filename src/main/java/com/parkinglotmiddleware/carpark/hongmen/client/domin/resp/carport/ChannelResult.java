package com.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import lombok.Data;

/**
 * 通道结果
 *
 * @author Suhuyuan
 */
@Data
public class ChannelResult {
    /**
     * 通道 id
     * <p>
     * 只在停车场内部唯一
     */
    private String channelId;

    /**
     * 通道名称
     */
    private String channleName;

    /**
     * 通行方向
     */
    private String direction;
}
