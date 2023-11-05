package com.parkinglotmiddleware.domain.manager.container.service.ability.coupon;

import lombok.Data;

/**
 * 优惠券结果
 *
 * @author Suhuyuan
 */
@Data
public class CouponMessageResult {
    /**
     * 优惠券领用的唯一ID
     */
    private String couponId;
    /**
     * 发行编号
     */
    private String publishNo;
    /**
     * 优惠券名称
     */
    private String couponName;
    /**
     * true正常可用 false不可用
     */
    private Boolean status;
}
