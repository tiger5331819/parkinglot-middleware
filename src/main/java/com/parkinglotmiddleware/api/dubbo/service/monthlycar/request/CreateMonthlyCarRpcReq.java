package com.parkinglotmiddleware.api.dubbo.service.monthlycar.request;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 创建月租车请求
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class CreateMonthlyCarRpcReq extends ParkingLotRpcReq {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H
     */
    private Integer cardTypeId;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime startTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime endTime;
    /**
     * 收费金额 单位：元
     */
    private BigDecimal money;
    /**
     * 0 现金 1微信 2支付宝
     */
    private int payType;

    /**
     * 人员名称
     */
    private String contactName;

    /**
     * 人员手机号码
     */
    private String contactPhone;
}
