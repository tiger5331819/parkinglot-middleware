package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @ParameterizedTest
    @CsvSource({"Daoer,X24400000001", "Daoer,X52361700001", "Daoer,",
            ",X24400000001", ",X52361700001", ","})
    void parkingManagerConfigurationTest(String parkingLotManagerName, Long parkingLotId) {
        List cfgList = factory.getParkingLotConfiguration(parkingLotManagerName, parkingLotId);
        assertNotNull(cfgList);
        assertNotEquals(0, cfgList.size());
        cfgList.forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    void getManagerSupportTest(){
        Set cfgList=factory.getManagerSupport();
        assertNotNull(cfgList);
        assertNotEquals(0,cfgList.size());
        cfgList.forEach(item->{
            System.out.println(item);
        });
    }

    @ParameterizedTest
    @CsvSource({"Daoer,24400000001", "Daoer,52361700001", "Daoer,",
            ",24400000001", ",52361700001", ","})
    void parkingManagerHealthCheckTest(String parkingLotManagerName, Long parkingLotId) {
        Map<String, Map<Long, Boolean>> cfgList = factory.healthCheck(parkingLotManagerName, parkingLotId);
        assertNotNull(cfgList);
        assertNotEquals(0, cfgList.size());
        cfgList.forEach((managerName, result) -> {
            result.forEach((name, data) -> {
                System.out.println(managerName + "\t" + name + "\t" + data);
            });
        });
    }
}