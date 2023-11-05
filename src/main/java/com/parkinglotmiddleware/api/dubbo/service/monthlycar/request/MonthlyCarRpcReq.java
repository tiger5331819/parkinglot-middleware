package com.parkinglotmiddleware.api.dubbo.service.monthlycar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 获取月租车基本信息请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class MonthlyCarRpcReq extends ParkingLotRpcReq {
    /**
     * 车牌号码
     */
    private String carNo;
}
