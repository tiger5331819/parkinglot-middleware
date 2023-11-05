package com.parkinglotmiddleware.api.dubbo.service.carfee;

import com.parkinglotmiddleware.api.dubbo.service.carfee.request.CarFeeRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.ChannelCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.OrderPayMessageRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.response.CarOrderResultByListRpcResp;
import com.yfkyframework.common.core.exception.ExposerException;

/**
 * 停车场费用服务
 *
 * @author Suhuyuan
 */
public interface ICarFeeService {
     /**
      * 车辆缴费
      * (支持欠费)
      *
      * @param orderPayMessageRpcReq 车辆订单缴费信息
      * @return
      */
     void payAccess(OrderPayMessageRpcReq orderPayMessageRpcReq) throws ExposerException;

     /**
      * 临时车出场（获取车辆费用）
      *
      * @param carFeeRpcReq 获取车辆费用请求
      * @return
      */
     CarOrderResultByListRpcResp getCarFee(CarFeeRpcReq carFeeRpcReq) throws ExposerException;

     /**
      * 根据通道号获取车辆费用信息
      *
      * @param channelCarRpcReq      通道车辆信息请求
      * @return
      */
     CarOrderResultByListRpcResp getChannelCarFee(ChannelCarRpcReq channelCarRpcReq) throws ExposerException;
}
