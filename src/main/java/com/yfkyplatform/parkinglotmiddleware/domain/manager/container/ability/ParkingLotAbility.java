package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;

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
     * @return
     */
    default ICarPortAblitity carport() {
        throw new UnsupportedOperationException("不支持工具能力");
    }

    /**
     * 优惠券
     * @return
     */
    default ICouponAblitity coupon(){
        throw new UnsupportedOperationException("不支持工具能力");
    }

    /**
     * 访客
     * @return
     */
    default IGuestAblitity guest(){
        throw new UnsupportedOperationException("不支持工具能力");
    }

    /**
     * 月卡
     * @return
     */
    default IMonthlyAblitity monthly(){
        throw new UnsupportedOperationException("不支持工具能力");
    }
}
