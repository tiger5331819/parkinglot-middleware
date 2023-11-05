package com.parkinglotmiddleware.api.dubbo.service.carfee.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 获取车辆费用请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class CarFeeRpcReq extends ParkingLotRpcReq {

    /**
     * 车牌号码
     */
    private String carNo;
}
