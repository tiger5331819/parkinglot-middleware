package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon;

import lombok.Data;

/**
 * 优惠券结果
 *
 * @author Suhuyuan
 */
@Data
public class CouponResult {
    /**
     * 发行编号
     */
    private String publishNo;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * 优惠券编号
     */
    private int couponNo;
    /**
     * 1正常可用 2不可用
     */
    private int status;
    /**
     * 优惠券领用的唯一ID
     */
    private String objectId;
}
