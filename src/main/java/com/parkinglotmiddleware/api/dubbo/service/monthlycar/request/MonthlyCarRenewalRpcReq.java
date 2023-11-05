package com.parkinglotmiddleware.api.dubbo.service.monthlycar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 月租车续期请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class MonthlyCarRenewalRpcReq extends ParkingLotRpcReq {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime newStartTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime newEndTime;
    /**
     * 收费金额
     */
    private BigDecimal money;
    /**
     * 0 现金
     * 1 微信
     * 2 支付宝
     */
    private int payType;
}
