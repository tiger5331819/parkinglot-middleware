package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.IMonthlyCarService;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.CreateMonthlyCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.MonthlyCarRenewalRpcReq;
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
 * 测试控制器
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
        return monthlyCarService.monthlyCarLongRentalRate(parkingLotManager, parkingLotId, null);
    }

    @Operation(summary =  "获取月租车续费信息")
    @GetMapping("/{carNo}/fee")
    public MonthlyCarFeeResultRpcResp monthlyCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        return monthlyCarService.monthlyCarFee(parkingLotManager, parkingLotId, carNo, null);
    }

    @Operation(summary =  "获取月租车基本信息")
    @GetMapping("/{carNo}")
    public MonthlyCarMessageResultRpcResp monthlyCarInfo(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        return monthlyCarService.monthlyCarInfo(parkingLotManager, parkingLotId, carNo, null);
    }

    @Operation(summary =  "获取月租车缴费历史")
    @GetMapping("/{carNo}/History")
    public List<MonthlyCarHistoryMessageResultRpcResp> monthlyCarHistory(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        return monthlyCarService.monthlyCarHistory(parkingLotManager, parkingLotId, carNo, null);
    }

    @Operation(summary =  "创建月租车")
    @PostMapping("/{carNo}")
    public Boolean createMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo, @RequestBody CreateMonthlyCarRpcReq createMonthlyCarRpcReq) {
        return monthlyCarService.createMonthlyCar(parkingLotManager, parkingLotId, createMonthlyCarRpcReq, null);
    }

    @Operation(summary =  "月租车续期")
    @PatchMapping("/{carNo}/renewal")
    public Boolean renewalMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo, @RequestBody MonthlyCarRenewalRpcReq monthlyCarRenewal) {
        return monthlyCarService.renewalMonthlyCar(parkingLotManager, parkingLotId, monthlyCarRenewal, null);
    }

    @Operation(summary =  "月租车销户")
    @DeleteMapping("/{carNo}")
    public Boolean removeMonthlyCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        return monthlyCarService.removeMonthlyCar(parkingLotManager, parkingLotId, carNo, null);
    }
}
