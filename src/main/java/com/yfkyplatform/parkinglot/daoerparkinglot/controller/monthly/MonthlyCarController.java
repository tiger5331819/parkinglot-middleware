package com.yfkyplatform.parkinglot.daoerparkinglot.controller.monthly;

import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerMonthlyCar;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.monthlycar.MonthlyCarResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.monthly.request.RenewalMonthlyCarRequest;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManager;
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
@RequestMapping(value = "/api/monthlycar")
@RestController
public class MonthlyCarController {

    private IDaoerMonthlyCar api;

    public MonthlyCarController(ParkingLotManager manager){
        api=(IDaoerMonthlyCar)manager.parkingLot("DaoerTest");
    }

    @ApiOperation(value = "获取停车场下的月卡费率")
    @GetMapping(value = "/longrentalrate")
    public DaoerBaseResp<List<MonthlyCarLongRentalRateResult>> getLongRentalRate(){
        return api.getMonthlyCarLongRentalRate().block();
    }

    @ApiOperation(value = "获取月租车基本信息")
    @GetMapping(value = "/monthlycar/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> getCarInfo(@ApiParam(value = "车牌号") @PathVariable String carNo){
        return api.getMonthlyCarInfo(carNo).block();
    }

    @ApiOperation(value = "获取月租车历史缴费信息")
    @GetMapping(value = "/monthlycar/{carNo}/history")
    public DaoerBaseResp<List<MonthlyCarHistoryResult>> getCarHistory(@ApiParam(value = "车牌号") @PathVariable String carNo){
        return api.getMonthlyCarHistory(carNo).block();
    }

    @ApiOperation(value = "月租车续期")
    @PostMapping(value = "/monthlycar/{carNo}/renewal")
    public DaoerBaseResp<MonthlyCarResult> renewalCar(@ApiParam(value = "车牌号码") @PathVariable String carNo,@RequestBody RenewalMonthlyCarRequest request){
        return api.renewalMonthlyCar(carNo, request.getNewStartTime(), request.getNewEndTime(), request.getBalanceMoney(), request.getPayType()).block();
    }

    @ApiOperation(value = "月租车销户")
    @DeleteMapping(value = "/monthlycar/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> removeCar(@ApiParam(value = "车牌号码")@PathVariable String carNo){
        return api.removeMonthlyCar(carNo).block();
    }
}
