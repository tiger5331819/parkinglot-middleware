package com.parkinglotmiddleware.api.dubbo.service.carport.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 无牌车入场请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class BlankCarRpcReq extends ParkingLotRpcReq {

    /**
     * 微信或支付宝openid
     */
    private String openId;

    /**
     * 1 微信  2 支付宝
     */
    private int scanType;

    /**
     * 通道号
     */
    private String channelId;
}
