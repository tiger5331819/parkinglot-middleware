package com.yfkyplatform.parkinglot.domain.manager;

import com.yfkyplatform.parkinglot.daoerparkinglot.DaoerParkingLotManager;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void UseParkingManagerTest(){
        IDaoerTool tool= factory.manager("Daoer").parkingLot("DaoerTest");
        System.out.println(tool.getToken());
    }
}