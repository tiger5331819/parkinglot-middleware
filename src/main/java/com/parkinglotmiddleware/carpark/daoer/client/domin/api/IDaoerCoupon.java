package com.parkinglotmiddleware.carpark.daoer.client.domin.api;

import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 优惠券接口
 *
 * @author Suhuyuan
 */
public interface IDaoerCoupon {
    /**
     * 查询优惠券
     *
     * @return
     */
    Mono<DaoerBaseResp<List<CouponResult>>> getCoupon(String openId);

    /**
     * 使用优惠券
     *
     * @return
     */
    Mono<DaoerBaseResp<CouponUseResult>> useCoupon(String objectId, String carNo);
}
