package com.yfkyplatform.parkinglot.domain.manager.container.ability.coupon;

import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

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
     * @return
     */
    Mono<DaoerBaseResp<List<CouponResult>>> getCoupon(String openId);

    /**
     * 查询优惠券
     *
     * @return
     */
    Mono<DaoerBaseResp<List<CouponUseResult>>> useCoupon(String objectId, String carNo);
}
