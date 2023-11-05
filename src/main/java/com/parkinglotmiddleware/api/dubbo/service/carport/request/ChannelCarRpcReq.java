package com.parkinglotmiddleware.api.dubbo.service.carport.request;

import com.yfkyframework.common.core.domain.BaseRpcReq;
import lombok.Data;

/**
 * 通道车辆信息请求
 *
 * @author Suhuyuan
 */
@Data
public class ChannelCarRpcReq extends BaseRpcReq {

    private String openId;

    private int scanType;

    private String channelId;
}
