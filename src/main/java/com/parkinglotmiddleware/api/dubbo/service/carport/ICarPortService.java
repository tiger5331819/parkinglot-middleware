package com.parkinglotmiddleware.api.dubbo.service.carport;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.BlankCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.CarInfoRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarMessageRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarPortSpaceRpcResp;
import com.yfkyframework.common.core.exception.ExposerException;

/**
 * 停车场服务
 *
 * @author Suhuyuan
 */
public interface ICarPortService {
     /**
      * 无牌车入场
      *
      * @param blankCarRpcReq 无牌车入场请求
      * @return
      */
     String blankCarIn(BlankCarRpcReq blankCarRpcReq) throws ExposerException;

     /**
      * 车场余位
      *
      * @param parkingLotRpcReq 停车场信息
      * @return
      */
     CarPortSpaceRpcResp getCarPortSpace(ParkingLotRpcReq parkingLotRpcReq) throws ExposerException;

     /**
      * 获取车辆信息
      *
      * @param carInfoRpcReq 获取车辆信息请求
      * @return
      */
     CarMessageRpcResp getCarInfo(CarInfoRpcReq carInfoRpcReq) throws ExposerException;
}
