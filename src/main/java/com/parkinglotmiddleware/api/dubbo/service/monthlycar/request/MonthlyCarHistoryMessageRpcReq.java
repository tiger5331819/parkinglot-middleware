package com.parkinglotmiddleware.api.dubbo.service.monthlycar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 获取月租车缴费历史请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class MonthlyCarHistoryMessageRpcReq extends ParkingLotRpcReq {
    /**
     * 车牌号码
     */
    private String carNo;
}
