package com.parkinglotmiddleware.api.dubbo.service.monthlycar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 月租车销户请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class RemoveMonthlyCarRpcReq extends ParkingLotRpcReq {
    /**
     * 车牌号码
     */
    private String carNo;
}
