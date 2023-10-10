package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.CarInResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.CarPortSpaceResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ChannelInfoResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.monthly.MonthlyCarMessageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.ContextService;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.PayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Space;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@Slf4j
public class CarPortService {
    private final ParkingLotPod parkingLot;

    private final ContextService contextService;


    public CarPortService(ParkingLotPod parkingLot, ContextService contextService) {
        this.parkingLot = parkingLot;
        this.contextService = contextService;
    }

    public CarPortMessage parkingLotMessage() {
        CarPortSpaceResult carPortSpaceResult = parkingLot.ability().carport().getCarPortSpace();
        List<ChannelInfoResult> channelInfoResults = parkingLot.ability().carport().getChannelsInfo();
        ParkingLotConfiguration configuration = parkingLot.configuration();

        CarPortMessage carPortMessage = new CarPortMessage();
        carPortMessage.setConfiguration(configuration);
        carPortMessage.setHealthCheck(parkingLot.healthCheck());
        carPortMessage.setRest(carPortSpaceResult.getRest());
        carPortMessage.setTotal(carPortSpaceResult.getTotal());
        carPortMessage.setUse(carPortSpaceResult.getUse());
        carPortMessage.setChannelList(channelInfoResults);

        return carPortMessage;
    }

    public Car refresh(String carNo) {
        Car car = contextService.get(carNo);
        return refresh(car);
    }

    private Car refresh(Car car) {
        PageResult<CarInResult> page = parkingLot.ability().carport().getCarInInfo(car.getCarNo(), null, null, 1, 10);

        Optional<CarInResult> carInResultOptional = page.getList().stream().filter(item -> StrUtil.equals(item.getCarNo(), car.getCarNo())).findFirst();
        if (carInResultOptional.isPresent()) {
            CarInResult carInResult = carInResultOptional.get();
            car.setTypeId(carInResult.getCardTypeId());
            car.setInPic(carInResult.getInPic());
            car.setInId(carInResult.getInId());
            car.setInTime(carInResult.getInTime());
        }

        MonthlyCarMessageResult monthlyCarInfo = parkingLot.ability().monthly().getMonthlyCarInfo(car.getCarNo());
        car.setTypeId(monthlyCarInfo.getCardTypeId());
        car.setTypeStartTime(monthlyCarInfo.getStartTime());
        car.setTypeEndTime(monthlyCarInfo.getEndTime());
        car.setTypeStatus(monthlyCarInfo.getStatus());
        car.setContactName(monthlyCarInfo.getContactName());
        car.setContactPhone(monthlyCarInfo.getContactPhone());

        contextService.update(car);
        return car;
    }

    public Car getCar(String carNo) {
        Car car = contextService.get(carNo);

        if (!AssertTool.checkCarNotNull(car)) {
            Car refreshCar = contextService.createCar(carNo);
            car = refresh(refreshCar);
        }
        return car;
    }

    public Car calculatePayMessage(String carNo) {
        Car car = getCar(carNo);

        CarOrderResult carOrderResult;

        Space carSpace = car.getCarSpace();
        if (StrUtil.isNotBlank(carSpace.getChannelId())) {
            Car channelCar = calculatePayMessage(carSpace.getChannelId(), 0, null);
            if (StrUtil.isNotBlank(channelCar.getCarNo())&&StrUtil.equals(car.getCarNo(),channelCar.getCarNo())) {
                return channelCar;
            }
        }

        carOrderResult = parkingLot.ability().fee().getCarFeeInfo(carNo);

        car.makeOrder(carOrderResult);
        contextService.update(car);
        return car;
    }

    public Car calculatePayMessage(String channelId, int scanType, String openId) {
        Car car;
        CarOrderResult carOrderResult = parkingLot.ability().fee().getCarFeeInfoByChannel(channelId, scanType, openId);
        if (StrUtil.isNotBlank(carOrderResult.getCarNo())) {
            car = getCar(carOrderResult.getCarNo());
            car.makeOrder(carOrderResult);
            Space carSpace=car.getCarSpace();
            carSpace.setChannelId(channelId);

            contextService.update(car);
        } else {
            car = new Car();
        }
        return car;
    }

    public void payFee(CarOrderPayMessage payMessage) {
        Car car = refresh(payMessage.getCarNo());
        PayMessage order = null;
        //根据inId查询订单
        if (ObjectUtil.isNotNull(payMessage.getInId())) {
            order =car.findOrder(payMessage.getInId());
            if (ObjectUtil.isNull(order)) {
                car = calculatePayMessage(payMessage.getCarNo());
                order = car.findOrder(payMessage.getInId());
            }
        }
        //获取当前订单
        if (ObjectUtil.isNull(order)) {
            order = car.getOrder();
            if (ObjectUtil.isNull(order)) {
                order = calculatePayMessage(payMessage.getCarNo()).getOrder();
            }
        }

        if (ObjectUtil.isNull(order)) {
            log.error(parkingLot.carPort().parkingLotMessage().toString()+"\n"+car+"\n"+"找不到订单，支付信息： " + payMessage);
            throw new NoSuchElementException("支付失败，找不到需要支付的订单");
        }

        payMessage.setInTime(order.getInTime());
        payMessage.setCreateTime(order.getCreateTime());
        payMessage.setInId(order.getInId());

        Space carSpace = car.getCarSpace();
        if (StrUtil.isNotBlank(carSpace.getChannelId())) {
            payMessage.setChannelId(carSpace.getChannelId());
        }

        Boolean success = parkingLot.ability().fee().payCarFee(payMessage);
        if (success) {
            contextService.remove(payMessage.getCarNo());
        }else{
            log.error("支付异常："+parkingLot.carPort().parkingLotMessage().toString()+"\n"+car+"\n"+"支付信息： " + payMessage);
            throw new RuntimeException(payMessage.getCarNo()+"支付失败，请查看异常信息");
        }
    }

    public Car updateSpace(String carNo,Space newCarSpace){
        Car car=getCar(carNo);
        if(ObjectUtil.isNotNull(newCarSpace)){
            car.setCarSpace(newCarSpace);
        }
        contextService.update(car);
        return car;
    }
}
