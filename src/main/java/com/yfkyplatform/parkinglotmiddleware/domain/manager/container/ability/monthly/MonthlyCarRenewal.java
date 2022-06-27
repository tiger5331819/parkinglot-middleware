package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 月租车续期信息
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarRenewal {
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
    private String money;
    /**
     * 0 现金 1微信 2支付宝
     */
    private int payType;
}
