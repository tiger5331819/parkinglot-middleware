package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.JieShunClient;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.ICarFeeAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunParkingLot extends ParkingLotPod {
    private final JieShunClient jieShun;

    public JieShunParkingLot(JieShunParkingLotConfiguration jieShunParkingLotInfo, RedisTool redis) {
        super(jieShunParkingLotInfo, redis);
        jieShun = new JieShunClient("", 3);
    }

    @Override
    public <T> T client() {
        JieShunParkingLotConfiguration configuration = (JieShunParkingLotConfiguration) cfg;
        return (T) new JieShunClient("", -1);
    }

    @Override
    public Boolean healthCheck() {
        try {
            return true;
            //return jieShun.getCarPortInfo().block().getHead().getStatus() == 1;
        } catch (Exception ex) {
            log.error(cfg.getId() + "健康检查异常", ex);
            return false;
        }
    }

    /**
     * 工具
     *
     * @return
     */
    @Override
    public IToolAblitity tool() {
        throw new UnsupportedOperationException();
        //return new JieShunToolAbility(jieShun);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        throw new UnsupportedOperationException();
        //return new JieShunCarPortAbility(jieShun, redis);
    }

    /**
     * 车场缴费
     *
     * @return
     */
    @Override
    public ICarFeeAblitity fee() {
        throw new UnsupportedOperationException();
        //return new JieShunCarFeeAbility(jieShun, redis);
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
        //return new JieShunMonthlyCarAbility(jieShun);
    }


}
