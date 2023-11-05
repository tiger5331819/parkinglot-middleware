package com.parkinglotmiddleware.carpark.daoer.ability;

import com.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCoupon;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.coupon.CouponMessageResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.coupon.CouponUseMessage;
import com.parkinglotmiddleware.domain.manager.container.service.ability.coupon.ICouponAblitity;

import java.util.ArrayList;
import java.util.List;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class DaoerCouponAbility implements ICouponAblitity {

    private final IDaoerCoupon api;

    public DaoerCouponAbility(IDaoerCoupon daoerClient){
        api=daoerClient;
    }

    /**
     * 查询优惠券
     *
     * @param openId 用户 openID
     * @return
     */
    @Override
    public List<CouponMessageResult> getCoupon(String openId) {
        List<CouponResult> couponResults=api.getCoupon(openId).block().getBody();
        List<CouponMessageResult> results=new ArrayList<>();
        couponResults.forEach(item->{
            CouponMessageResult result=new CouponMessageResult();
            result.setCouponId(item.getObjectId());
            result.setPublishNo(item.getPublishNo());
            result.setCouponName(item.getCouponName());
            result.setStatus(item.getStatus()==1);

            results.add(result);
        });
        return results;
    }

    /**
     * 使用优惠券
     *
     * @param couponUseMessageList
     * @return
     */
    @Override
    public List<CouponUseResult> useCoupon(List<CouponUseMessage> couponUseMessageList) {
        return null;
    }
}
