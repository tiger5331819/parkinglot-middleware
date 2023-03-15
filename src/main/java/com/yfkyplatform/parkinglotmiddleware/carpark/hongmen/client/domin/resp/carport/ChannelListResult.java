package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import lombok.Data;

import java.util.List;

/**
 * 通道列表结果
 *
 * @author Suhuyuan
 */
@Data
public class ChannelListResult {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 停车场名称
     */
    private String parkingName;

    /**
     * 通道列表
     */
    private List<ChannelResult> channels;

    /**
     * 状态码描述
     */
    private String message;


}
