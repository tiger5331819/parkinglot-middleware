package com.parkinglotmiddleware.api.dubbo.service.carfee.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 通道车辆信息请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class ChannelCarRpcReq extends ParkingLotRpcReq {

    /**
     * 微信或支付宝openid
     */
    private String openId;

    /**
     * 1微信2支付宝
     */
    private int scanType;

    /**
     * 通道号
     */
    private String channelId;
}
