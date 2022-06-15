package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerCarPortAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerGuestAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerMonthlyCarAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerToolAbility;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
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
    private DaoerClient Daoer;

    public DaoerParkingLot(DaoerParkingLotConfiguration daoerParkingLotInfo, RedisTool redis){
        super(daoerParkingLotInfo);
        Daoer=new DaoerClient(daoerParkingLotInfo.getId(),daoerParkingLotInfo.getAppName(),daoerParkingLotInfo.getParkId(),daoerParkingLotInfo.getBaseUrl(),redis);
    }

    @Override
    public <T> T client() {
        return (T) Daoer;
    }

    @Override
    public boolean healthCheck() {
        return Daoer.healthCheck();
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
        return new DaoerCarPortAbility(Daoer);
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
