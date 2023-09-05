package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerMonthlyCar;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.SpecialMonthlyCarResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.CreateMonthlyCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.RenewalMonthlyCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.SpecialCarRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 月卡车控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "月卡车")
@RequestMapping(value= "/Daoer/api/{parkingLotId}/monthlycar")
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

    @Operation(summary =  "获取停车场下的月卡费率")
    @GetMapping(value= "/longrentalrate")
    public DaoerBaseResp<List<MonthlyCarLongRentalRateResult>> getLongRentalRate(@PathVariable String parkingLotId) {
        return api(parkingLotId).getMonthlyCarLongRentalRate().block();
    }

    @Operation(summary =  "获取月租车基本信息")
    @GetMapping(value= "/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> getCarInfo(@PathVariable String parkingLotId, @Parameter(description =  "车牌号") @PathVariable String carNo) {
        return api(parkingLotId).getMonthlyCarInfo(carNo).block();
    }

    @Operation(summary =  "获取月租车历史缴费信息")
    @GetMapping(value= "/{carNo}/history")
    public DaoerBaseResp<List<MonthlyCarHistoryResult>> getCarHistory(@PathVariable String parkingLotId, @Parameter(description =  "车牌号") @PathVariable String carNo) {
        return api(parkingLotId).getMonthlyCarHistory(carNo).block();
    }

    @Operation(summary =  "创建月租车")
    @PostMapping(value= "/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> createCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo, @RequestBody CreateMonthlyCarRequest request) {
        return api(parkingLotId).createMonthlyCar(carNo, request.getCardTypeId(), request.getStartTime(), request.getEndTime(), request.getBalanceMoney(), request.getPayType(), request.getContactName(), request.getContactPhone()).block();
    }

    @Operation(summary =  "月租车续期")
    @PostMapping(value= "/{carNo}/renewal")
    public DaoerBaseResp<MonthlyCarResult> renewalCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo, @RequestBody RenewalMonthlyCarRequest request) {
        return api(parkingLotId).renewalMonthlyCar(carNo, request.getNewStartTime(), request.getNewEndTime(), request.getBalanceMoney(), request.getPayType()).block();
    }

    @Operation(summary =  "月租车销户")
    @DeleteMapping(value= "/{carNo}")
    public DaoerBaseResp<MonthlyCarResult> removeCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo) {
        return api(parkingLotId).removeMonthlyCar(carNo).block();
    }

    @Operation(summary =  "查询特殊车辆")
    @GetMapping(value= "/special")
    public DaoerBaseResp<PageModel<SpecialMonthlyCarResult>> getSpecialCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") Integer pageIndex, Integer pageSize) {
        return api(parkingLotId).getSpecialMonthlyCar(pageIndex, pageSize).block();
    }

    @Operation(summary =  "新增修改特殊车辆")
    @PostMapping(value= "/special/{carNo}")
    public DaoerBaseResp saveSpecialCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo, @RequestBody SpecialCarRequest request) {
        return api(parkingLotId).specialMonthlyCar(carNo, request.getCarNoType(), request.getIsStop(), request.getDescription(), request.getObjectId()).block();
    }
}
