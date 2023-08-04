package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen;

import com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.HongmenParkingLotClient;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;

/**
 * 洪门停车场容器
 *
 * @author Suhuyuan
 */

public class HongmenParkingLot extends ParkingLotPod {
    private final HongmenParkingLotClient hongmen;

    public HongmenParkingLot(HongmenParkingLotConfiguration hongmenParkingLotInfo, RedisTool redis) {
        super(hongmenParkingLotInfo, redis);
        hongmen = new HongmenParkingLotClient(hongmenParkingLotInfo.getAppId(), hongmenParkingLotInfo.getSecret(), hongmenParkingLotInfo.getBaseUrl());
    }

    @Override
    public <T> T client() {
        return (T) hongmen;
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
        throw new UnsupportedOperationException();
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        throw new UnsupportedOperationException();
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
