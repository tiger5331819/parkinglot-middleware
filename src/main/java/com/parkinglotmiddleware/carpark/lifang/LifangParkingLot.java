package com.parkinglotmiddleware.carpark.lifang;

import com.parkinglotmiddleware.carpark.lifang.ability.LifangAbilityService;
import com.parkinglotmiddleware.carpark.lifang.client.LifangParkingLotClient;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.parkinglotmiddleware.universal.RedisTool;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */

public class LifangParkingLot extends ParkingLotPod {
    private final LifangParkingLotClient Lifang;

    public LifangParkingLot(LifangParkingLotConfiguration lifangParkingLotInfo, RedisTool redis) {
        super(lifangParkingLotInfo, redis);
        Lifang = new LifangParkingLotClient(lifangParkingLotInfo.getSecret(), lifangParkingLotInfo.getBaseUrl());
    }

    @Override
    public ParkingLotAbilityService ability() {
        return new LifangAbilityService(Lifang, redis);
    }

    @Override
    public <T> T client() {
        return (T) Lifang;
    }

    @Override
    public Boolean healthCheck() {
        return true;
    }




}
