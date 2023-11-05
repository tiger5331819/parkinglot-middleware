package com.parkinglotmiddleware.api.web.open.duecar;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.api.web.open.duecar.req.DueCarConfigurationSyncReq;
import com.parkinglotmiddleware.api.web.open.duecar.req.FindDueCarConfigurationReq;
import com.parkinglotmiddleware.api.web.open.duecar.req.FindDueCarReq;
import com.parkinglotmiddleware.api.web.open.duecar.resp.FindDueCarConfigurationResp;
import com.parkinglotmiddleware.api.web.open.duecar.resp.FindDueCarResp;
import com.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.parkinglotmiddleware.universal.duecar.DueCar;
import com.parkinglotmiddleware.universal.duecar.DueCarProxy;
import com.parkinglotmiddleware.universal.duecar.DueConfiguration;
import com.yfkyframework.util.context.AccountRpcContext;
import com.yfkyplatform.presspay.api.resp.PresspayUrgePayConfResp;
import com.yfkyplatform.presspay.api.resp.QueryUrgePayMsgRpcResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 联合催缴接口
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "联合催缴")
@RestController
@RequestMapping("/duecar")
public class DueCarOpenController {

    private final DueCarProxy dueCarService;

    private final ParkingLotManagerFactory factory;

    public DueCarOpenController(DueCarProxy dueCarService, ParkingLotManagerFactory factory) {
        this.dueCarService = dueCarService;
        this.factory = factory;
    }

    @Operation(summary = "查询是否是催缴车辆")
    @PostMapping("/{operatorId}/find")
    public FindDueCarResp findDueCar(@PathVariable Integer operatorId, @Validated @RequestBody FindDueCarReq findDueCarReq) {

        ParkingLotConfiguration configuration = factory.getParkingLotConfigurationByThirdId(findDueCarReq.getParkNo(), operatorId);

        DueCar dueCar = new DueCar();
        dueCar.setPlateNumber(findDueCarReq.getPlateNumber());
        dueCar.setPlateColor(findDueCarReq.getPlateColor());
        dueCar.setVehicleType(findDueCarReq.getVehicleType());

        QueryUrgePayMsgRpcResp result = dueCarService.checkDueCar(operatorId, configuration, dueCar,findDueCarReq.getInOrOut());
        FindDueCarResp resp= BeanUtil.copyProperties(result, FindDueCarResp.class);

        if (result.getDueCar() == 1) {
            AccountRpcContext.setOperatorId(operatorId);
            ParkingLotPod parkingLot = factory.manager(configuration.getManagerType()).parkingLot(configuration.getId());

            Car car=parkingLot.carPort().calculatePayMessage(findDueCarReq.getDsn(),1,null);
            if(ObjectUtil.isNull(car.getOrder())){
                resp.setDueCar(1);
                log.info("不进行联动催缴，返回参数：" + resp);
            }else{
                parkingLot.dueCar().addDueCar(findDueCarReq.getPlateNumber(), findDueCarReq.getDsn(), findDueCarReq.getInOrOut());
                resp.setDueCar(2);
                log.info("联动催缴返回参数：" + resp);
            }

        }else{
            resp.setDueCar(1);
        }
        return resp;
    }

    @Operation(summary = "催缴配置同步通知")
    @PostMapping("/{operatorId}/configuration/sync")
    public void SyncDueCarConfiguration(@PathVariable Integer operatorId, @RequestBody DueCarConfigurationSyncReq dueCarConfigurationSyncReq) {

        ParkingLotConfiguration configuration = factory.getParkingLotConfigurationByThirdId(dueCarConfigurationSyncReq.getParkNo(), operatorId);

        if (ObjectUtil.isNull(dueCarConfigurationSyncReq.getUrgepayNotIn())) {
            dueCarConfigurationSyncReq.setUrgepayNotIn(1);
        }
        if (ObjectUtil.isNull(dueCarConfigurationSyncReq.getUrgepayNotOut())) {
            dueCarConfigurationSyncReq.setUrgepayNotIn(1);
        }

        DueConfiguration cfg = new DueConfiguration();
        cfg.setUrgepayNotIn(dueCarConfigurationSyncReq.getUrgepayNotIn() == 1 ? 0 : 1);
        cfg.setUrgepayNotOut(dueCarConfigurationSyncReq.getUrgepayNotOut() == 1 ? 0 : 1);
        cfg.setStartTime(dueCarConfigurationSyncReq.getStartTime());
        cfg.setCloseTime(dueCarConfigurationSyncReq.getCloseTime());

        dueCarService.syncDueCarConfiguration(operatorId, configuration, cfg);
    }

    @Operation(summary = "查询当前车场的联动配置")
    @PostMapping("/{operatorId}/configuration/find")
    public FindDueCarConfigurationResp findDueCarConfiguration(@PathVariable Integer operatorId, @RequestBody FindDueCarConfigurationReq findDueCarConfigurationReq) {

        ParkingLotConfiguration configuration = factory.getParkingLotConfigurationByThirdId(findDueCarConfigurationReq.getParkNo(), operatorId);

        PresspayUrgePayConfResp result = dueCarService.findDueCarConfiguration(operatorId, configuration);

        FindDueCarConfigurationResp resp = BeanUtil.copyProperties(result, FindDueCarConfigurationResp.class);

        resp.setUrgepayNotIn(resp.getUrgepayNotIn() == 1 ? 0 : 1);
        resp.setUrgepayNotOut(resp.getUrgepayNotOut() == 1 ? 0 : 1);

        return resp;
    }
}
