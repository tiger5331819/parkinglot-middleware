package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.carpark;


import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class CarFeePay extends DaoerBase {

    /**
     * 支付时间 yyyy-MM-dd HH:mm:ss
     */
    private String payTime;
    /**
     * 停车时长 单位：分钟
     */
    private int duration;
    /**
     * 应收金额 单位：元
     */
    private double totalAmount;
    /**
     * 优惠金额 单位：元
     */
    private double disAmount;
    /**
     * 0 出口缴费 1 定点缴费 2 超时缴费
     */
    private int paymentType;
    /**
     * 支付类型0，现金支付，1微信支付,2，支付宝支付,3,银联闪付
     */
    private int payType;
    /**
     * 支付订单编号 唯一
     */
    private String paymentTnx;
    /**
     * 实收金额,单位：元
     */
    private double couponAmount;
    /**
     * 通道ID
     */
    private String dsn;

    public CarFeePay(String uri) {
        super(uri);
    }

    public void setPayTime(String payTime) {
        if(!StrUtil.isBlank(payTime)) {
            this.payTime = payTime;
        }
    }

    public void setPaymentTnx(String paymentTnx) {
        if(!StrUtil.isBlank(payTime)) {
            this.paymentTnx = paymentTnx;
        }
    }

    public void setDsn(String dsn) {
        if(!StrUtil.isBlank(payTime)) {
            this.dsn = dsn;
        }
    }
}
