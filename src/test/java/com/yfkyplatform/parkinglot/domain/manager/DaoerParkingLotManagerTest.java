package com.yfkyplatform.parkinglot.domain.manager;

import com.yfkyplatform.parkinglot.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.CarPortSpaceResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("dev")
@SpringBootTest
class DaoerParkingLotManagerTest {

    @Autowired
    ParkingLotManagerFactory factory;

    @ParameterizedTest
    @CsvSource({"Daoer,DaoerTest"})
    void clientTest(String managerName,String parkingLotId){
        /*ParkingLotManager manager= factory.manager(managerName);
        ParkingLotPod parkingLotPod= manager.parkingLot(parkingLotId);
        DaoerClient client=parkingLotPod.client();*/

        DaoerClient client=factory.manager(managerName).parkingLot(parkingLotId).client();
        assertNotNull(client);
    }

    @ParameterizedTest
    @CsvSource({"Daoer,DaoerTest"})
    void healthCheckTest(String managerName,String parkingLotId){
        Boolean check=factory.manager(managerName).parkingLot(parkingLotId).healthCheck();
        assertTrue(check);
    }

    @ParameterizedTest
    @CsvSource({"Daoer,DaoerTest"})
    void carPortTest(String managerName,String parkingLotId){
        CarPortSpaceResult result=factory.manager(managerName).parkingLot(parkingLotId).carport().getCarPortSpace();
        assertNotNull(result);
        System.out.println(result);
    }
}