package com.parkinglotmiddleware.domain.manager.container.service.ability.monthly;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 月租车缴费历史结果
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarHistoryMessageResult {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 缴费金额
     */
    private BigDecimal amount;
    /**
     * 0 现金 1微信 2支付宝
     */
    private int payType;
    /**
     * 月租区间开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String startTime;
    /**
     * 月租区间结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;
    /**
     * 人员姓名
     */
    private String concatName;
    /**
     * 0开户 1续期 6 销户
     */
    private int operatorType;
}
