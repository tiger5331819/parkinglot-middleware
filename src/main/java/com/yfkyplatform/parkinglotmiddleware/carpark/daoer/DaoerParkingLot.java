package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability.DaoerAbilityService;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.CarInData;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
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

    private final TestBox testBox;

    public DaoerParkingLot(DaoerParkingLotConfiguration daoerParkingLotInfo, RedisTool redis, TestBox testBox) {
        super(daoerParkingLotInfo, redis);
        this.testBox=testBox;
        daoer = new DaoerClient(daoerParkingLotInfo.getId(), daoerParkingLotInfo.getAppName(), daoerParkingLotInfo.getParkId(), daoerParkingLotInfo.getBaseUrl(), daoerParkingLotInfo.getImgUrl(), redis, 6,testBox);
    }

    @Override
    public ParkingLotAbilityService ability() {
        return new DaoerAbilityService(daoer, configuration(), redis);
    }

    @Override
    public <T> T client() {
        DaoerParkingLotConfiguration configuration = (DaoerParkingLotConfiguration) cfg;
        return (T) new DaoerClient(configuration.getId(), configuration.getAppName(), configuration.getParkId(), configuration.getBaseUrl(), configuration.getImgUrl(), redis, -1,testBox);
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

}
