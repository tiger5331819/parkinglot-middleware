package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.duecar;

import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.IDueCarService;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.request.DueCarConfigurationRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.duecar.request.DueCarSuccessRpcReq;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport.DueCarService;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Space;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.yfkyplatform.parkinglotmiddleware.universal.duecar.DueCar;
import com.yfkyplatform.parkinglotmiddleware.universal.duecar.DueCarProxy;
import com.yfkyplatform.presspay.api.resp.QueryUrgePayMsgRpcResp;
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

    private final DueCarProxy dueCarProxy;

    public DueCarServiceExposer(ParkingLotManagerFactory factory, DueCarProxy dueCarProxy) {
        this.factory = factory;
        this.dueCarProxy = dueCarProxy;
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

        DueCarService dueCarService=parkingLot.dueCar();
        Space space=dueCarService.findDucCar(dueCarSuccessRpcReq.getCarNo());

        DueCar dueCar = new DueCar();
        dueCar.setPlateNumber(dueCarSuccessRpcReq.getCarNo());

        QueryUrgePayMsgRpcResp result = dueCarProxy.checkDueCar(dueCarSuccessRpcReq.getOperatorId(), parkingLot.configuration(), dueCar,space.getLocation());

        if(result.getDueCar()==1){
            log.warn("车辆"+dueCarSuccessRpcReq.getCarNo()+" 补缴情况不达标");
            return;
        }

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
