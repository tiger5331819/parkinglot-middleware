package com.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import lombok.Data;

/**
 * 临时车缴费结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeePayResult {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 停车支付订单号
     */
    private String parkingOrder;

}
