package com.parkinglotmiddleware.api.dubbo.service.duecar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 补缴成功通知
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class DueCarSuccessRpcReq extends ParkingLotRpcReq {

    /**
     * 车牌号码
     */
    private String carNo;
}
