package com.parkinglotmiddleware.api.web.bussiness;

import com.parkinglotmiddleware.api.dubbo.service.duecar.IDueCarService;
import com.parkinglotmiddleware.api.dubbo.service.duecar.request.DueCarConfigurationRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.duecar.request.DueCarSuccessRpcReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 联合催缴控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "联合催缴控制器")
@RequestMapping(value= "api/{parkingLotManager}/{parkingLotId}/duecar")
@RestController
public class DueCarController {

    private final IDueCarService dueCarService;

    public DueCarController(IDueCarService service){
        dueCarService=service;
    }

    @Operation(summary = "同步补缴配置信息")
    @PostMapping("/configDueCar")
    public void configDueCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId,@RequestBody DueCarConfigurationRpcReq rpcReq) {
        rpcReq.setParkingLotManagerCode(parkingLotManager);
        rpcReq.setParkingLotId(parkingLotId);

        dueCarService.configDueCar(rpcReq);
    }

    @Operation(summary =  "补缴成功通知")
    @PostMapping("/dueCarAccess")
    public void dueCarAccess(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody DueCarSuccessRpcReq rpcReq) {
        rpcReq.setParkingLotManagerCode(parkingLotManager);
        rpcReq.setParkingLotId(parkingLotId);

        dueCarService.dueCarAccess(rpcReq);
    }

}
