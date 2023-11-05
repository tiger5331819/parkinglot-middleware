package com.parkinglotmiddleware.carpark.daoer.ability;

import com.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
import com.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carfee.ICarFeeAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carport.ICarPortAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.coupon.ICouponAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.guest.IGuestAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.IMonthlyAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.tool.IToolAblitity;
import com.parkinglotmiddleware.universal.RedisTool;

/**
 * 立方车场能力服务
 *
 * @author Suhuyuan
 */

public class DaoerAbilityService extends ParkingLotAbilityService {

    private final DaoerClient daoer;

    private final RedisTool redis;

    private final DaoerParkingLotConfiguration configuration;

    public DaoerAbilityService(DaoerClient daoer, DaoerParkingLotConfiguration configuration, RedisTool redis) {
        this.daoer = daoer;
        this.redis = redis;
        this.configuration = configuration;
    }

    /**
     * 工具
     *
     * @return
     */
    @Override
    public IToolAblitity tool() {
        return new DaoerToolAbility(daoer);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        return new DaoerCarPortAbility(daoer);
    }

    /**
     * 车场缴费
     *
     * @return
     */
    @Override
    public ICarFeeAblitity fee() {
        return new DaoerCarFeeAbility(daoer, configuration, redis);
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
        return new DaoerGuestAbility(daoer);
    }

    /**
     * 月卡
     *
     * @return
     */
    @Override
    public IMonthlyAblitity monthly() {
        return new DaoerMonthlyCarAbility(daoer);
    }
}
