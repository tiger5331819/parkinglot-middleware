package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;

import java.util.List;

/**
 * 优惠券接口
 *
 * @author Suhuyuan
 */
public interface ICouponAblitity {
    /**
     * 查询优惠券
     *
     * @param openId 用户 openID
     * @return
     */
    List<CouponMessageResult> getCoupon(String openId);

    /**
     * 使用优惠券
     *
     * @return
     */
    List<CouponUseResult> useCoupon(List<CouponUseMessage> couponUseMessageList);
}
