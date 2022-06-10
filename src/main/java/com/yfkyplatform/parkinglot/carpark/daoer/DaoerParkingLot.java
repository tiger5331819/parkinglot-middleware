package com.yfkyplatform.parkinglot.carpark.daoer;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.carpark.daoer.ability.DaoerCarPortAbility;
import com.yfkyplatform.parkinglot.carpark.daoer.ability.DaoerToolAbility;
import com.yfkyplatform.parkinglot.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.tool.IToolAblitity;

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
        String token=Daoer.getToken();
        return !StrUtil.isEmpty(token)&&!StrUtil.isBlank(token);
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
