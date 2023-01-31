package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 月租车续期信息
 *
 * @author Suhuyuan
 */
@Data
public class CreateMonthlyCar {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime startTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime endTime;
    /**
     * 卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H
     */
    private Integer cardTypeId;
    /**
     * 收费金额
     */
    private String balanceMoney;
    /**
     * 0 现金 1微信 2支付宝
     */
    private int payType;

    /**
     * 人员名称
     */
    private String concatName;

    /**
     * 人员手机号码
     */
    private String concatPhone;
}
