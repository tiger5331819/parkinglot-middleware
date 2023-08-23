package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.ICarFeeAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.tool.IToolAblitity;

/**
 * 停车场能力接口
 * @author Suhuyuan
 */
public interface ParkingLotAbility {
    /**
     * 工具
     * @return
     */
    default IToolAblitity tool(){
        throw new UnsupportedOperationException("不支持工具能力");
    }

    /**
     * 车场
     *
     * @return
     */
    default ICarPortAblitity carport() {
        throw new UnsupportedOperationException("不支持车场能力");
    }

    /**
     * 车场缴费
     *
     * @return
     */
    default ICarFeeAblitity fee() {
        throw new UnsupportedOperationException("不支持车场缴费能力");
    }

    /**
     * 优惠券
     *
     * @return
     */
    default ICouponAblitity coupon() {
        throw new UnsupportedOperationException("不支持优惠卷能力");
    }

    /**
     * 访客
     * @return
     */
    default IGuestAblitity guest(){
        throw new UnsupportedOperationException("不支持访客能力");
    }

    /**
     * 月卡
     * @return
     */
    default IMonthlyAblitity monthly(){
        throw new UnsupportedOperationException("不支持月卡能力");
    }
}
