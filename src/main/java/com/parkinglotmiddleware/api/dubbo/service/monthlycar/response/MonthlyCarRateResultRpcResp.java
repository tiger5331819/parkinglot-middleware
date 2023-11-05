package com.parkinglotmiddleware.api.dubbo.service.monthlycar.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 月卡费率
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarRateResultRpcResp extends BaseRpcResp {
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
     * 月卡持续时间
     */
    private String packageDurationMessage;
    /**
     * 备注
     */
    private String remark;
}
