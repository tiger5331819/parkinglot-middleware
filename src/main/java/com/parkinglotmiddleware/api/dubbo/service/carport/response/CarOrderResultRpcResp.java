package com.parkinglotmiddleware.api.dubbo.service.carport.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费订单结果
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderResultRpcResp extends BaseRpcResp {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime startTime;
    /**
     * 订单创建时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime createTime;

    /**
     * 该车离场时间（计费时间） yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime outTime;
    /**
     * 停车时长  单位:分
     */
    private int serviceTime;
    /**
     * 应缴金额,单位：分
     */
    private BigDecimal totalFee;
    /**
     * 实缴金额,单位：分
     */
    private BigDecimal payFee;
    /**
     * 优惠金额,单位：分
     */
    private BigDecimal discountFee;

    /**
     * 超时时间  单位:分
     */
    private int overTime;

    /**
     * 0 出口缴费 1 定点缴费 2 超时缴费
     */
    private Integer paymentType;

    /**
     * 欠费车场编号，欠费支付需传
     */
    private String parkingNo;

    /**
     * 入场记录唯一标识
     */
    private String inId;


    public void setFee(BigDecimal payFee, BigDecimal discountFee) {
        if (payFee.compareTo(new BigDecimal(0)) > -1) {
            this.payFee = payFee;
        }
        if (discountFee.compareTo(new BigDecimal(0)) > -1) {
            this.discountFee = discountFee;
        }
        this.totalFee = this.payFee.add(this.discountFee);
    }
}
