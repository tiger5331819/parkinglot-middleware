package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerMonthlyCar;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.RenewalMonthlyCarRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 月卡车控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"月卡车"})
@RequestMapping(value = "/Daoer/api/{parkingLotId}/monthlycar")
@IgnoreCommonResponse
@RestController
public class DaoerMonthlyCarController {

    private final ParkingLotManager manager;

    private IDaoerMonthlyCar api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerMonthlyCarController(DaoerParkingLotManager manager) {
        this.manager = manager;
    }

    @ApiOperation(value = "获取停车场下的月卡费率")
    @GetMapping(value = "/longrentalrate")
    public DaoerBaseResp<List<MonthlyCarLongRentalRateResult>> getLongRentalRate(@PathVariable String parkingLotId) {
        return api(parkingLotId).getMonthlyCarLongRentalRate().block();
    }

    @ApiOperation(value = "获取月租车基本信息")
    @GetMapping(value = "/monthlycar/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> getCarInfo(@PathVariable String parkingLotId, @ApiParam(value = "车牌号") @PathVariable String carNo) {
        return api(parkingLotId).getMonthlyCarInfo(carNo).block();
    }

    @ApiOperation(value = "获取月租车历史缴费信息")
    @GetMapping(value = "/monthlycar/{carNo}/history")
    public DaoerBaseResp<List<MonthlyCarHistoryResult>> getCarHistory(@PathVariable String parkingLotId, @ApiParam(value = "车牌号") @PathVariable String carNo) {
        return api(parkingLotId).getMonthlyCarHistory(carNo).block();
    }

    @ApiOperation(value = "月租车续期")
    @PostMapping(value = "/monthlycar/{carNo}/renewal")
    public DaoerBaseResp<MonthlyCarResult> renewalCar(@PathVariable String parkingLotId, @ApiParam(value = "车牌号码") @PathVariable String carNo, @RequestBody RenewalMonthlyCarRequest request) {
        return api(parkingLotId).renewalMonthlyCar(carNo, request.getNewStartTime(), request.getNewEndTime(), request.getBalanceMoney(), request.getPayType()).block();
    }

    @ApiOperation(value = "月租车销户")
    @DeleteMapping(value = "/monthlycar/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> removeCar(@PathVariable String parkingLotId, @ApiParam(value = "车牌号码") @PathVariable String carNo) {
        return api(parkingLotId).removeMonthlyCar(carNo).block();
    }
}
