package com.yfkyplatform.parkinglotmiddleware.api.web.bussiness;

import com.yfkyplatform.parkinglotmiddleware.api.ParkingLotRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.IMonthlyCarService;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.*;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.MonthlyCarFeeResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.MonthlyCarHistoryMessageResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.MonthlyCarMessageResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.MonthlyCarRateResultRpcResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 月卡控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "月卡控制器")
@RequestMapping(value= "api/{parkingLotManager}/{parkingLotId}/monthlycar")
@RestController
public class MonthlyCarController {

    private final IMonthlyCarService monthlyCarService;

    public MonthlyCarController(IMonthlyCarService service) {
        monthlyCarService = service;
    }

    @Operation(summary =  "获取停车场下的月卡费率")
    @GetMapping("/rentalRate")
    public List<MonthlyCarRateResultRpcResp> monthlyCarLongRentalRate(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId) {
        ParkingLotRpcReq parkingLotRpcReq=new CreateMonthlyCarRpcReq();
        parkingLotRpcReq.setParkingLotManagerCode(parkingLotManager);
        parkingLotRpcReq.setParkingLotId(parkingLotId);

        return monthlyCarService.monthlyCarLongRentalRate(parkingLotRpcReq);
    }

    @Operation(summary =  "获取月租车续费信息")
    @GetMapping("/{carNo}/fee")
    public MonthlyCarFeeResultRpcResp monthlyCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        MonthlyCarFeeRpcReq monthlyCarFeeRpcReq=new MonthlyCarFeeRpcReq();
        monthlyCarFeeRpcReq.setCarNo(monthlyCarFeeRpcReq.getCarNo());
        monthlyCarFeeRpcReq.setParkingLotManagerCode(parkingLotManager);
        monthlyCarFeeRpcReq.setParkingLotId(parkingLotId);

        return monthlyCarService.monthlyCarFee(monthlyCarFeeRpcReq);
    }

    @Operation(summary =  "获取月租车基本信息")
    @GetMapping("/{carNo}")
    public MonthlyCarMessageResultRpcResp monthlyCarInfo(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        MonthlyCarRpcReq monthlyCarRpcReq=new MonthlyCarRpcReq();
        monthlyCarRpcReq.setCarNo(carNo);
        monthlyCarRpcReq.setParkingLotManagerCode(parkingLotManager);
        monthlyCarRpcReq.setParkingLotId(parkingLotId);

        return monthlyCarService.monthlyCarInfo(monthlyCarRpcReq);
    }

    @Operation(summary =  "获取月租车缴费历史")
    @GetMapping("/{carNo}/History")
    public List<MonthlyCarHistoryMessageResultRpcResp> monthlyCarHistory(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        MonthlyCarHistoryMessageRpcReq monthlyCarHistoryMessageRpcReq=new MonthlyCarHistoryMessageRpcReq();
        monthlyCarHistoryMessageRpcReq.setCarNo(carNo);
        monthlyCarHistoryMessageRpcReq.setParkingLotManagerCode(parkingLotManager);
        monthlyCarHistoryMessageRpcReq.setParkingLotId(parkingLotId);

        return monthlyCarService.monthlyCarHistory(monthlyCarHistoryMessageRpcReq);
    }

    @Operation(summary =  "创建月租车")
    @PostMapping()
    public Boolean createMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody CreateMonthlyCarRpcReq createMonthlyCarRpcReq) {
        createMonthlyCarRpcReq.setParkingLotManagerCode(parkingLotManager);
        createMonthlyCarRpcReq.setParkingLotId(parkingLotId);
        return monthlyCarService.createMonthlyCar(createMonthlyCarRpcReq);
    }

    @Operation(summary =  "月租车续期")
    @PatchMapping("/renewal")
    public Boolean renewalMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId,@RequestBody MonthlyCarRenewalRpcReq monthlyCarRenewal) {
        monthlyCarRenewal.setParkingLotManagerCode(parkingLotManager);
        monthlyCarRenewal.setParkingLotId(parkingLotId);
        return monthlyCarService.renewalMonthlyCar(monthlyCarRenewal);
    }

    @Operation(summary =  "月租车销户")
    @DeleteMapping("/{carNo}")
    public Boolean removeMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        RemoveMonthlyCarRpcReq removeMonthlyCarRpcReq=new RemoveMonthlyCarRpcReq();
        removeMonthlyCarRpcReq.setCarNo(carNo);
        removeMonthlyCarRpcReq.setParkingLotManagerCode(parkingLotManager);
        removeMonthlyCarRpcReq.setParkingLotId(parkingLotId);

        return monthlyCarService.removeMonthlyCar(removeMonthlyCarRpcReq);
    }
}
