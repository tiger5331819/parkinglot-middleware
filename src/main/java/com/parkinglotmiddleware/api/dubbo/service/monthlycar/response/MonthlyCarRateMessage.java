package com.parkinglotmiddleware.api.dubbo.service.monthlycar.response;

import com.yfkyframework.common.core.model.convert.Convert;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 月卡费率
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarRateMessage extends Convert {
    /**
     * 月卡费用
     */
    private BigDecimal packageCharge;
    /**
     * 月卡持续时间
     */
    private String duration;
    /**
     * 月卡持续时间描述
     */
    private String durationMessage;
    /**
     * 备注
     */
    private String remark;
}
