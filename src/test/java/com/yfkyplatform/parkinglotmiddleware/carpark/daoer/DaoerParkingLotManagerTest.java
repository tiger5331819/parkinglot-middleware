package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class DaoerParkingLotManagerTest {

    @Autowired
    ParkingLotManager daoerManager;

    @Autowired
    public DaoerParkingLotManagerTest(ParkingLotManagerFactory factory){
        daoerManager=factory.manager("Daoer");
    }

    @ParameterizedTest
    @CsvSource({"52361700001,d813cfb7959548f9a5f311286d713150,X52361700001,https://parklot.q-parking.com,科盈测试",
            "24400000001,a909fb0eb10240979b2b374273bf6342,X24400000001,https://parklot.q-parking.com,铜陵"})
    void addCongfiguration(Long id, String appName, String parkId, String baseUrl, String description) throws JsonProcessingException {
        DaoerParkingLotConfiguration cfg = new DaoerParkingLotConfiguration(id, appName, parkId, baseUrl, description);

        assertTrue(daoerManager.addParkingLot(cfg));
    }

    @ParameterizedTest
    @CsvSource({"24400000001", "52361700001"})
    void clientTest(Long parkingLotId) {
        /*ParkingLotPod parkingLotPod= daoerManager.parkingLot(parkingLotId);
        DaoerClient client=parkingLotPod.client();*/

        DaoerClient client = daoerManager.parkingLot(parkingLotId).client();
        assertNotNull(client);
    }

    @ParameterizedTest
    @CsvSource({"铜陵", "科盈测试"})
    void healthCheckTest(Long parkingLotId) {
        Boolean check = daoerManager.parkingLot(parkingLotId).healthCheck();
        assertTrue(check);
    }

    @ParameterizedTest
    @CsvSource({"铜陵", "科盈测试"})
    void carPortTest(Long parkingLotId) {
        assertThrows(RuntimeException.class, () -> daoerManager.parkingLot(parkingLotId).carport().getCarPortSpace());
    }
}