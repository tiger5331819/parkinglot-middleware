package com.yfkyplatform.parkinglotmiddleware.carpark.lifang;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability.LifangCarPortAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability.LifangToolAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.LifangClient;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */

public class LifangParkingLot extends ParkingLotPod {
    private final LifangClient Lifang;

    public LifangParkingLot(LifangParkingLotConfiguration lifangParkingLotInfo, RedisTool redis) {
        super(lifangParkingLotInfo, redis);
        Lifang = new LifangClient(lifangParkingLotInfo.getSecret(), lifangParkingLotInfo.getBaseUrl(), redis);
    }

    @Override
    public <T> T client() {
        return (T) Lifang;
    }

    @Override
    public Boolean healthCheck() {
        return true;
    }

    /**
     * 工具
     *
     * @return
     */
    @Override
    public IToolAblitity tool() {
        return new LifangToolAbility(Lifang);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        return new LifangCarPortAbility(Lifang, redis);
    }

    /**
     * 优惠券
     *
     * @return
     */
    @Override
    public ICouponAblitity coupon() {
        throw new UnsupportedOperationException();
    }

    /**
     * 访客
     *
     * @return
     */
    @Override
    public IGuestAblitity guest() {
        throw new UnsupportedOperationException();
    }

    /**
     * 月卡
     *
     * @return
     */
    @Override
    public IMonthlyAblitity monthly() {
        throw new UnsupportedOperationException();
    }


}
