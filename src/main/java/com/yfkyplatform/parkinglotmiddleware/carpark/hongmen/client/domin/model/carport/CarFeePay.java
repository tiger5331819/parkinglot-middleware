package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.model.carport;


import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePay {

    /**
     * 停车场编号
     */
    private String parkingId;
    /**
     * 车牌号码
     * <p>
     * 与 cardNo 二选一
     */
    private String carPlateNo;
    /**
     * 停车卡号
     * <p>
     * 与 carPlateNo 二选一，若两者都提供，则 cardNo 优先
     */
    private String cardNo;
    /**
     * 停车流水号
     */
    private String parkingSerial;

    private String parkingOrder;
    /**
     * 支付时间 yyyyMMddHHmmss
     */
    private String payTime;
    /**
     * 支付金额 单位：分
     */
    private int payValue;
    /**
     * 支付类型
     * <p>
     * 0:现金,1:微信,2:支付宝,3:其它支付平台，4:停车券
     */
    private String payType;

    /**
     * 支付类型 如：“微信”
     */
    private String payOriginDesc;

    /**
     * 第三方订单号(调用方)
     */
    private String orderNum;

    public void setPayTime(LocalDateTime payTime) {
        if (ObjectUtil.isNotNull(payTime)) {
            this.payTime = payTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
    }
}
