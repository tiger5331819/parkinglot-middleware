package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon;

import lombok.Data;

/**
 * 优惠券使用信息
 *
 * @author Suhuyuan
 */
@Data
public class CouponUseMessage {
    /**
     * 优惠券Id
     */
    String couponId;
    /**
     * 车牌号
     */
    String carNo;
}
