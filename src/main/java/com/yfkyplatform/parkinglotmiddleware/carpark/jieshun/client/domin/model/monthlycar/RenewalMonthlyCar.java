package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.monthlycar;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 月租车续期
 *
 * @author Suhuyuan
 */
@Data
public class RenewalMonthlyCar extends JieShunBase {
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String newStartTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String newEndTime;
    /**
     * 收费金额
     */
    private String balanceMoney;
    /**
     * 0 现金 1微信 2支付宝
     */
    private int payType;

    public void setNewStartTime(String newStartTime) {
        if (!StrUtil.isBlank(newStartTime)) {
            this.newStartTime = newStartTime;
        }
    }

    public void setNewEndTime(String newEndTime) {
        if (!StrUtil.isBlank(newEndTime)) {
            this.newEndTime = newEndTime;
        }
    }

    public void setBalanceMoney(String balanceMoney) {
        if (!StrUtil.isBlank(balanceMoney)) {
            this.balanceMoney = balanceMoney;
        }
    }
}
