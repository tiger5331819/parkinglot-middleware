package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.coupon;

import lombok.Data;

/**
 * 优惠券使用结果
 *
 * @author Suhuyuan
 */
@Data
public class CouponUseResult {
    /**
     * 1使用成功 2使用失败
     */
    private int usestatus;
    /**
     * 优惠券唯一标识
     */
    private String objectId;
}
