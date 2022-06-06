package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.monthlycar;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 月租车续期
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class RenewalMonthlyCar extends DaoerBase {
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

    public RenewalMonthlyCar(String uri) {
        super(uri);
    }

    public void setNewStartTime(String newStartTime) {
        if(!StrUtil.isBlank(newStartTime)) {
            this.newStartTime = newStartTime;
        }
    }

    public void setNewEndTime(String newEndTime) {
        if(!StrUtil.isBlank(newEndTime)) {
            this.newEndTime = newEndTime;
        }
    }

    public void setBalanceMoney(String balanceMoney) {
        if(!StrUtil.isBlank(balanceMoney)) {
            this.balanceMoney = balanceMoney;
        }
    }
}
