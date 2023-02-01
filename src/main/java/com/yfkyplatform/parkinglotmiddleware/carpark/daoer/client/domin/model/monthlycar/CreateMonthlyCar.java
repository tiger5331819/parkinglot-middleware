package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 创建月租车
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class CreateMonthlyCar extends DaoerBase {
    /**
     * 卡类型编号：11~18:月租车A~H,41~42:免费车A~B,51~58:储值车A~H
     */
    private Integer cardTypeId;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private String startTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String endTime;
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
    private String contactName;

    /**
     * 人员手机号码
     */
    private String concatPhone;

    public CreateMonthlyCar(String uri) {
        super(uri);
    }

    public void setBalanceMoney(String balanceMoney) {
        if (!StrUtil.isBlank(balanceMoney)) {
            this.balanceMoney = balanceMoney;
        }
    }
}
