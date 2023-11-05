package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 月卡费率结果
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarLongRentalRateResult {
    /**
     * 月卡类型
     */
    private int packageType;
    /**
     * 月卡名称
     */
    private String packageName;
    /**
     * 月卡费用
     */
    private BigDecimal packageCharge;
    /**
     * 月卡持续时间
     */
    private String packageDuration;
    /**
     * 备注
     */
    private String remark;
}
