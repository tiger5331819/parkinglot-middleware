package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.CarInData;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.ICarFeeAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.coupon.ICouponAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.tool.IToolAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerParkingLot extends ParkingLotPod{
    private final DaoerClient daoer;

    public DaoerParkingLot(DaoerParkingLotConfiguration daoerParkingLotInfo, RedisTool redis) {
        super(daoerParkingLotInfo, redis);
        daoer = new DaoerClient(daoerParkingLotInfo.getId(), daoerParkingLotInfo.getAppName(), daoerParkingLotInfo.getParkId(), daoerParkingLotInfo.getBaseUrl(), daoerParkingLotInfo.getImgUrl(), redis, 3);
    }

    @Override
    public <T> T client() {
        DaoerParkingLotConfiguration configuration = (DaoerParkingLotConfiguration) cfg;
        return (T) new DaoerClient(configuration.getId(), configuration.getAppName(), configuration.getParkId(), configuration.getBaseUrl(), configuration.getImgUrl(), redis, -1);
    }

    @Override
    public Boolean healthCheck() {
        try {
            DaoerBaseResp<PageModel<CarInData>> carInList = daoer.getCarInInfo(null, null, null, 1, 10).block();
            List<CarInData> carInDataList = carInList.getBody().getList();
            if (AssertTool.checkCollectionNotNull(carInDataList)) {
                Optional<CarInData> carInDataOptional = carInDataList.stream().findFirst();
                if (carInDataOptional.isPresent()) {
                    return daoer.getCarFeeInfoWithArrear(carInDataOptional.get().getCarNo()).block().getHead().getStatus() == 1;
                }
            }
            return carInList.getHead().getStatus() == 1;

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
        return new DaoerToolAbility(daoer);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        return new DaoerCarPortAbility(daoer, redis);
    }

    /**
     * 车场缴费
     *
     * @return
     */
    @Override
    public ICarFeeAblitity fee() {
        return new DaoerCarFeeAbility(daoer, redis);
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
