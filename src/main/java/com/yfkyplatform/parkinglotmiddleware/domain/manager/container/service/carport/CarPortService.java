package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderWithArrearResultByList;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.CarInResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.CarPortSpaceResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ChannelInfoResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.monthly.MonthlyCarMessageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.ContextService;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.PayMessage;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        carPortMessage.setCarNumber(carPortSpaceResult.getCarNumber());
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
        CarOrderResult carOrderResult = parkingLot.ability().fee().getCarFeeInfo(carNo);

        setOrder(car, carOrderResult);
        return car;
    }

    public Car calculatePayMessage(String channelId, int scanType, String openId) {
        Car car;
        CarOrderResult carOrderResult = parkingLot.ability().fee().getCarFeeInfoByChannel(channelId, scanType, openId);
        if (StrUtil.isNotBlank(carOrderResult.getCarNo())) {
            car = getCar(carOrderResult.getCarNo());
            setOrder(car, carOrderResult);
        } else {
            car = new Car();
        }
        return car;
    }

    private void setOrder(Car car, CarOrderResult carOrderResult) {
        PayMessage payMessage = new PayMessage();
        payMessage.setCreateTime(carOrderResult.getCreateTime());
        payMessage.setInTime(carOrderResult.getStartTime());
        payMessage.setTotalFee(carOrderResult.getTotalFee());
        payMessage.setPayFee(carOrderResult.getPayFee());
        payMessage.setDiscountFee(carOrderResult.getDiscountFee());

        car.setOrder(payMessage);

        if (carOrderResult instanceof CarOrderWithArrearResultByList) {
            CarOrderWithArrearResultByList arrearResultByList = (CarOrderWithArrearResultByList) carOrderResult;
            payMessage.setInId(arrearResultByList.getInId());
            payMessage.setOverTime(arrearResultByList.getOverTime());

            if (AssertTool.checkCollectionNotNull(arrearResultByList.getArrearList())) {
                car.setArrearOrder(arrearResultByList.getArrearList().stream().map(arrear -> {
                    PayMessage arrearPayMessage = new PayMessage();
                    arrearPayMessage.setCreateTime(arrear.getCreateTime());
                    arrearPayMessage.setInTime(arrear.getStartTime());
                    arrearPayMessage.setTotalFee(arrear.getTotalFee());
                    arrearPayMessage.setPayFee(arrear.getPayFee());
                    arrearPayMessage.setDiscountFee(arrear.getDiscountFee());
                    arrearPayMessage.setInId(arrear.getInId());
                    arrearPayMessage.setOverTime(arrear.getOverTime());
                    return arrearPayMessage;
                }).collect(Collectors.toList()));
            }
        }

        contextService.update(car);
    }

    private PayMessage findOrder(Car car, String inId) {
        if (ObjectUtil.isNotNull(car.getOrder())) {
            if (StrUtil.equals(car.getOrder().getInId(), inId)) {
                return car.getOrder();
            }
        }
        if (AssertTool.checkCollectionNotNull(car.getArrearOrder())) {
            List<PayMessage> orderList = car.getArrearOrder();
            Optional<PayMessage> orderOptional = orderList.stream().filter(item -> StrUtil.equals(item.getInId(), inId)).findFirst();
            if (orderOptional.isPresent()) {
                return orderOptional.get();
            }
        }
        return null;
    }

    public Boolean payFee(CarOrderPayMessage payMessage) {
        Car car = refresh(payMessage.getCarNo());
        PayMessage order = null;
        if (ObjectUtil.isNotNull(payMessage.getInId())) {
            order = findOrder(car, payMessage.getInId());
            if (ObjectUtil.isNull(order)) {
                car = calculatePayMessage(payMessage.getCarNo());
                order = findOrder(car, payMessage.getInId());
            }
        }
        if (ObjectUtil.isNull(order)) {
            order = car.getOrder();
            if (ObjectUtil.isNull(order)) {
                order = calculatePayMessage(payMessage.getCarNo()).getOrder();
            }
        }

        if (ObjectUtil.isNull(order)) {
            log.error(parkingLot.carPort().parkingLotMessage().toString());
            log.error(car.toString());
            log.error("找不到订单，支付信息： " + payMessage);
            return false;
        }

        payMessage.setInTime(order.getInTime());
        payMessage.setCreateTime(order.getCreateTime());
        payMessage.setInId(order.getInId());

        Boolean success = parkingLot.ability().fee().payCarFeeAccess(payMessage);
        if (success) {
            contextService.remove(payMessage.getCarNo());
        }
        return success;
    }
}
