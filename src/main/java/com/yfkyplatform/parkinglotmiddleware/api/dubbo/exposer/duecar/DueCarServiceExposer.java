package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.duecar;

import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.IDueCarService;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.request.DueCarConfigurationRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.request.DueCarSuccessRpcReq;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@DubboService(timeout = 3000)
@Component
@Slf4j
public class DueCarServiceExposer implements IDueCarService {

    private final ParkingLotManagerFactory factory;

    public DueCarServiceExposer(ParkingLotManagerFactory factory) {
        this.factory = factory;
    }

    /**
     * 补缴成功通知
     *
     * @param dueCarSuccessRpcReq 补缴成功信息
     * @return
     */
    @Override
    public void dueCarAccess(DueCarSuccessRpcReq dueCarSuccessRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(dueCarSuccessRpcReq.getOperatorId());

        ParkingLotPod parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(dueCarSuccessRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(dueCarSuccessRpcReq.getParkingLotId());

        parkingLot.dueCar().dueCarSuccess(dueCarSuccessRpcReq.getCarNo());
    }

    /**
     * 同步补缴配置信息
     *
     * @param dueCarConfigurationRpcReq 同步补缴配置信息请求
     * @return
     */
    @Override
    public void configDueCar(DueCarConfigurationRpcReq dueCarConfigurationRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(dueCarConfigurationRpcReq.getOperatorId());

        ParkingLotPod parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(dueCarConfigurationRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(dueCarConfigurationRpcReq.getParkingLotId());

        parkingLot.ability().carport().configDueCar(dueCarConfigurationRpcReq.getUrgepayNotIn(),dueCarConfigurationRpcReq.getUrgepayNotOut(),
                dueCarConfigurationRpcReq.getStartTime(),dueCarConfigurationRpcReq.getCloseTime());

    }
}
