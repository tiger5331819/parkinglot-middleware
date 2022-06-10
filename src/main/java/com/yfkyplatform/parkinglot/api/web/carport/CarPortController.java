package com.yfkyplatform.parkinglot.api.web.carport;

import com.yfkyplatform.parkinglot.api.web.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglot.api.web.carport.request.OrderPayMessage;
import com.yfkyplatform.parkinglot.api.web.carport.response.CarPortSpace;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.CarOrderPayMessage;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.CarOrderResult;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.CarPortSpaceResult;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.ICarPortAblitity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场控制器"})
@RequestMapping(value = "api/{parkingLotManager}/{parkingLotId}/carport")
@RestController
public class CarPortController {

    private ParkingLotManagerFactory factory;

    public CarPortController(ParkingLotManagerFactory factory){
        this.factory=factory;
    }

    @ApiOperation(value = "车场余位")
    @GetMapping("/space")
    public CarPortSpace getCarPortSpace(@PathVariable String parkingLotManager, @PathVariable String parkingLotId){
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        CarPortSpaceResult carPortSpace= carPortService.getCarPortSpace();

        CarPortSpace result=new CarPortSpace();
        result.setRest(carPortSpace.getRest());
        result.setTotal(carPortSpace.getTotal());

        return result;
    }

    @ApiOperation(value = "无牌车出场")
    @PostMapping("/blankCarOut")
    public CarOrderResult blankCarOut(@PathVariable String parkingLotManager, @PathVariable String parkingLotId, @RequestBody BlankCarRequest blankCar){
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        String carNo=carPortService.blankCarOut(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
        return carPortService.getCarFeeInfo(carNo);
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankCarIn")
    public String blankCarIn(@PathVariable String parkingLotManager, @PathVariable String parkingLotId, @RequestBody BlankCarRequest blankCar){
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        return carPortService.blankCarIn(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
    }

    @ApiOperation(value = "临时车出场（获取车辆费用）")
    @GetMapping("/{carNo}/Fee")
    public CarOrderResult blankCarIn(@PathVariable String parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo){
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        return carPortService.getCarFeeInfo(carNo);
    }

    @ApiOperation(value = "车辆缴费")
    @PatchMapping("/{carNo}/Fee")
    public Boolean payAccess(@PathVariable String parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo,@RequestBody OrderPayMessage payMessage){
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        CarOrderPayMessage message=new CarOrderPayMessage();
        message.setCarNo(carNo);
        message.setPayFee(payMessage.getPayFee());
        message.setChannelId(payMessage.getChannelId());
        message.setPayTime(payMessage.getPayTime());
        message.setPayType(payMessage.getPayType());
        message.setDiscountFee(payMessage.getDiscountFee());
        message.setPaymentTransactionId(payMessage.getPaymentTransactionId());
        message.setServiceTime(payMessage.getServiceTime());
        message.setTotalFee(payMessage.getTotalFee());
        message.setPaymentType(payMessage.getPaymentType());

        return carPortService.payCarFeeAccess(message);
    }
}
