package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.CarPortSpaceResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ChannelInfoResult;

import java.util.List;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */

public class CarPortService {
    private final ParkingLotPod parkingLot;


    public CarPortService(ParkingLotPod parkingLot) {
        this.parkingLot = parkingLot;
    }

    public CarPortMessage parkingLotMessage() {
        CarPortSpaceResult carPortSpaceResult = parkingLot.carport().getCarPortSpace();
        List<ChannelInfoResult> channelInfoResults = parkingLot.carport().getChannelsInfo();
        ParkingLotConfiguration configuration = parkingLot.configuration();

        CarPortMessage carPortMessage = new CarPortMessage();
        carPortMessage.setConfiguration(configuration);
        carPortMessage.setHealthCheck(parkingLot.healthCheck());
        carPortMessage.setRest(carPortSpaceResult.getRest());
        carPortMessage.setTotal(carPortMessage.getTotal());

        return carPortMessage;
    }
}
