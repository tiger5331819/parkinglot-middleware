package com.parkinglotmiddleware.api.dubbo.service.carport.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 获取车辆信息请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class CarInfoRpcReq extends ParkingLotRpcReq {

    /**
     * 车牌号码
     */
    private String carNo;
}
