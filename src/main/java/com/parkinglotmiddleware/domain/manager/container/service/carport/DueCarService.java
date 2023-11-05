package com.parkinglotmiddleware.domain.manager.container.service.carport;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.parkinglotmiddleware.domain.manager.container.service.context.PayMessage;
import com.parkinglotmiddleware.domain.manager.container.service.context.Space;
import com.parkinglotmiddleware.universal.RedisTool;
import com.yfkyframework.common.core.exception.ExposerException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@Slf4j
public class DueCarService {
    private final ParkingLotPod parkingLot;

    private final CarPortService carPortService;

    private final RedisTool redis;

    private final String contextPrefix = "duecar:";

    public DueCarService(ParkingLotPod parkingLot, CarPortService carPortService, RedisTool redis) {
        this.parkingLot = parkingLot;
        this.carPortService = carPortService;
        this.redis = redis;
    }

    private String makeKey(String carNo) {
        return contextPrefix + carNo;
    }

    /**
     * 添加催缴车辆
     * @param carNo
     * @param channelId
     */
    public void addDueCar(String carNo,String channelId,Integer location){
        Space carSpace=new Space();
        carSpace.setChannelId(channelId);
        carSpace.setLocation(location);

        redis.set(makeKey(carNo), carSpace, Duration.ofMinutes(10));
        log.info("催缴车辆"+carNo+"在通道被催缴，通道ID："+channelId);
        carPortService.updateSpace(carNo,carSpace);
    }

    /**
     * 查询催缴车辆被催缴位置
     * @param carNo
     * @return
     */
    public Space findDucCar(String carNo){
        Car car=carPortService.getCar(carNo);
        if(redis.check(makeKey(car.getCarNo()))){
            return redis.get(makeKey(car.getCarNo()));
        }else{
            throw new ExposerException(-1,"催缴车辆"+car.getCarNo()+"不存在通道催缴记录");
        }
    }

    /**
     * 催缴完成
     * @param carNo
     * @return
     */
    public void dueCarSuccess(String carNo){
        Car car=carPortService.getCar(carNo);

        if(redis.check(makeKey(car.getCarNo()))){
            Space carSpace=redis.get(makeKey(car.getCarNo()));
            carPortService.updateSpace(carNo,carSpace);

            switch (carSpace.getLocation()){
                case 1:parkingLot.ability().carport().dueCarAccess(carSpace.getChannelId(),carNo); break;
                case 2:payFreeOrder(carNo,carSpace.getChannelId());break;
                default:throw new UnsupportedOperationException("不受支持的催缴完成方式");
            }

            log.info("催缴车辆"+carNo+"在通道完成催缴，通道ID："+carSpace.getChannelId());
            redis.delete(makeKey(car.getCarNo()));
        }else{
            throw new ExposerException(-1,"催缴车辆"+car.getCarNo()+"不存在通道催缴记录");
        }
    }

    private void payFreeOrder(String carNo,String channelId){
        Car car=parkingLot.carPort().calculatePayMessage(carNo);
        PayMessage payMessage=car.getOrder();
        if(ObjectUtil.isNotNull(payMessage)){
            if(payMessage.getPayFee().compareTo(new BigDecimal(0))==0){
                CarOrderPayMessage carOrderPayMessage=new CarOrderPayMessage();
                carOrderPayMessage.setCarNo(carNo);
                carOrderPayMessage.setPayTime(LocalDateTime.now());
                carOrderPayMessage.setPayType(2000);
                carOrderPayMessage.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
                carOrderPayMessage.setDiscountFee(payMessage.getDiscountFee());
                carOrderPayMessage.setPayFee(payMessage.getPayFee());
                carOrderPayMessage.setInTime(payMessage.getInTime());
                carOrderPayMessage.setCreateTime(payMessage.getCreateTime());
                carOrderPayMessage.setInId(payMessage.getInId());
                carOrderPayMessage.setChannelId(channelId);

                parkingLot.carPort().payFee(carOrderPayMessage);
            }else{
                log.error("催缴车辆"+carNo+"当前订单金额不为0，当前订单金额："+payMessage);
                throw new RuntimeException(carNo+"当前订单金额不为0");
            }
        }else{
            log.error("催缴车辆"+carNo+"不存在当前订单");
            throw new RuntimeException(carNo+"不存在当前订单");
        }
    }
}
