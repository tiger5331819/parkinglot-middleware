package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api;

import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.coupon.CouponResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.coupon.CouponUseResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 优惠券接口
 *
 * @author tiger
 */
public interface IDaoerCoupon {
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
