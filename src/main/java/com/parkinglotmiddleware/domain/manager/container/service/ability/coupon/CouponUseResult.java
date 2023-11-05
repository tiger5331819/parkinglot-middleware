package com.parkinglotmiddleware.domain.manager.container.service.ability.coupon;

import lombok.Data;

/**
 * 优惠券使用结果
 *
 * @author Suhuyuan
 */
@Data
public class CouponUseResult {
    /**
     * 优惠券唯一标识
     */
    private String couponId;
    /**
     * true 使用成功 false使用失败
     */
    private Boolean success;

}
