package com.yfkyplatform.parkinglot.domain.manager;

import com.yfkyplatform.parkinglot.carpark.daoer.DaoerParkingLot;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.CarPortSpaceResult;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ParkingLotManagerFactoryTest {

    @Autowired
    ParkingLotManagerFactory factory;
    @Autowired
    List<ParkingLotManager> parkingLotManagerList;

    @Test
    void loadParkingLotManagerTest(){
        factory.loadParkingLotManager(parkingLotManagerList);
    }

    @Test
    void useParkingManagerTest(){
        IDaoerTool tool= factory.manager("Daoer").parkingLot("DaoerTest").client();
        System.out.println(tool.getToken());
    }


    @Test
    void useParkingManagerConfigurationTest(){
        ParkingLotConfiguration daoerParkingLotConfiguration = factory.manager("Daoer").parkingLot("DaoerTest").configuration();

        System.out.println(daoerParkingLotConfiguration);
    }
}