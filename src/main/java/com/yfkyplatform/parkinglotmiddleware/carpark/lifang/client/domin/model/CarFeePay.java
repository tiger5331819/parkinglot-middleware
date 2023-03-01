package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model;


import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePay {

    /**
     * 车牌号
     */
    private String carCode;
    /**
     * 卡号
     */
    private String serial;
    /**
     * 收费时间（统一缴费时间，格式：yyyy-MM-dd HH:mm:ss）
     */
    private String payTime;
    /**
     * 总金额（单位：分）=（实付金额+减免金额）
     */
    private BigDecimal chargeMoney;
    /**
     * 减免金额（单位：分）
     */
    private BigDecimal JMMoney;
    /**
     * 详见”附录2收费来源（chargeSource）字典”
     */
    private String chargeSource;
    /**
     * 收费方式(3:手持机，11:第三方接口收费)
     */
    private int chargeType;
    /**
     * 实付金额（单位：分）
     */
    private BigDecimal paidMoney;

    public void setPayTime(String payTime) {
        if (!StrUtil.isBlank(payTime)) {
            this.payTime = payTime;
        }
    }
}
