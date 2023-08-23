package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen;

import com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.HongmenParkingLotClient;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;

/**
 * 洪门停车场容器
 *
 * @author Suhuyuan
 */

public class HongmenParkingLot extends ParkingLotPod {
    private final HongmenParkingLotClient hongmen;

    public HongmenParkingLot(HongmenParkingLotConfiguration hongmenParkingLotInfo, RedisTool redis) {
        super(hongmenParkingLotInfo, redis);
        hongmen = new HongmenParkingLotClient(hongmenParkingLotInfo.getAppId(), hongmenParkingLotInfo.getSecret(), hongmenParkingLotInfo.getBaseUrl());
    }

    @Override
    public ParkingLotAbilityService ability() {
        return null;
    }

    @Override
    public <T> T client() {
        return (T) hongmen;
    }

    @Override
    public Boolean healthCheck() {
        return true;
    }

}
