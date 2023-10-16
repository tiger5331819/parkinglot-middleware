package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport;

import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Space;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

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
    public void addDueCar(String carNo,String channelId){
        Space carSpace=new Space();
        carSpace.setChannelId(channelId);

        redis.set(makeKey(carNo), channelId);
        log.info("催缴车辆"+carNo+"在通道被催缴，通道ID："+channelId, Duration.ofMinutes(10));
        carPortService.updateSpace(carNo,carSpace);
    }

    /**
     * 催缴完成
     * @param carNo
     * @return
     */
    public void dueCarSuccess(String carNo){
        Car car=carPortService.getCar(carNo);

        if(redis.check(makeKey(car.getCarNo()))){
            String channelId=redis.get(makeKey(car.getCarNo()));
            Space carSpace=new Space();
            carSpace.setChannelId(channelId);
            carPortService.updateSpace(carNo,carSpace);
            log.info("催缴车辆"+carNo+"在通道完成催缴，通道ID："+channelId);
            parkingLot.ability().carport().dueCarAccess(channelId,carNo);
        }else{
            throw new ExposerException(-1,"催缴车辆"+car.getCarNo()+"不存在通道催缴记录");
        }
    }
}
