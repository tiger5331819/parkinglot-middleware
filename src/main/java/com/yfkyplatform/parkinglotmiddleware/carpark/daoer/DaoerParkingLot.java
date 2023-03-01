package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerCarPortAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerGuestAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerMonthlyCarAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerToolAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
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

public class DaoerParkingLot extends ParkingLotPod{
    private final DaoerClient Daoer;

    public DaoerParkingLot(DaoerParkingLotConfiguration daoerParkingLotInfo, RedisTool redis) {
        super(daoerParkingLotInfo, redis);
        Daoer = new DaoerClient(daoerParkingLotInfo.getId(), daoerParkingLotInfo.getAppName(), daoerParkingLotInfo.getParkId(), daoerParkingLotInfo.getBaseUrl(), daoerParkingLotInfo.getImgUrl(), redis, 3);
    }

    @Override
    public <T> T client() {
        DaoerParkingLotConfiguration configuration = (DaoerParkingLotConfiguration) cfg;
        return (T) new DaoerClient(configuration.getId(), configuration.getAppName(), configuration.getParkId(), configuration.getBaseUrl(), configuration.getImgUrl(), redis, -1);
    }

    @Override
    public Boolean healthCheck() {
        return Daoer.getCarPortInfo().block().getHead().getStatus() == 1;
    }

    /**
     * 工具
     *
     * @return
     */
    @Override
    public IToolAblitity tool() {
        return new DaoerToolAbility(Daoer);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        return new DaoerCarPortAbility(Daoer, redis);
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
        return new DaoerGuestAbility(Daoer);
    }

    /**
     * 月卡
     *
     * @return
     */
    @Override
    public IMonthlyAblitity monthly() {
        return new DaoerMonthlyCarAbility(Daoer);
    }


}
