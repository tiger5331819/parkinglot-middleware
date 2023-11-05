package com.parkinglotmiddleware.api.web.bussiness;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.ICarPortService;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.BlankCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.CarInfoRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarMessageRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarPortSpaceRpcResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "车场控制器")
@RequestMapping(value= "api/{parkingLotManager}/{parkingLotId}/carport")
@RestController
public class CarPortController {

    private final ICarPortService carPortService;

    public CarPortController(ICarPortService service){
        carPortService=service;
    }

    @Operation(summary = "车场余位")
    @GetMapping("/space")
    public CarPortSpaceRpcResp getCarPortSpace(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId) {
        ParkingLotRpcReq parkingLotRpcReq=new ParkingLotRpcReq();
        parkingLotRpcReq.setParkingLotManagerCode(parkingLotManager);
        parkingLotRpcReq.setParkingLotId(parkingLotId);

        return carPortService.getCarPortSpace(parkingLotRpcReq);
    }

    @Operation(summary =  "无牌车入场")
    @PostMapping("/blankCarIn")
    public String blankCarIn(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody BlankCarRpcReq blankCarRpcReq) {
        blankCarRpcReq.setParkingLotManagerCode(parkingLotManager);
        blankCarRpcReq.setParkingLotId(parkingLotId);

        return carPortService.blankCarIn(blankCarRpcReq);
    }

    @Operation(summary =  "获取车辆信息")
    @GetMapping("/{carNo}")
    public CarMessageRpcResp getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, String carNo) {
        CarInfoRpcReq carInfoRpcReq=new CarInfoRpcReq();
        carInfoRpcReq.setCarNo(carNo);
        carInfoRpcReq.setParkingLotManagerCode(parkingLotManager);
        carInfoRpcReq.setParkingLotId(parkingLotId);

        return carPortService.getCarInfo(carInfoRpcReq);
    }


}
