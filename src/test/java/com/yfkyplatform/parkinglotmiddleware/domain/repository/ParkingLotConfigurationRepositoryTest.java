package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ParkingLotConfigurationRepositoryTest {

    @Autowired
    private IParkingLotConfigurationRepository repository;

    @Test
    void addTest() throws JsonProcessingException {
        DaoerConfiguration cfg = new DaoerConfiguration();
        cfg.setAppName("a909fb0eb10240979b2b374273bf6342");
        cfg.setParkId("X24400000001");
        cfg.setBaseUrl("https://parklot.q-parking.com");


        ParkingLotConfiguration<DaoerConfiguration> data = new ParkingLotConfiguration();
        data.setParkingLotId("24400000001");
        data.setParkingType("Daoer");
        data.setDescription("道尔测试");
        data.setConfig(cfg);

        repository.save(data);
    }

    @ParameterizedTest
    @CsvSource({"Daoer,2006001120010000"})
    void getTest(String type, String Id) {
        ParkingLotConfiguration<DaoerConfiguration> data = repository.findParkingLotConfigurationByParkingTypeAndParkingLotId(type, Id);
        System.out.println(data);
    }

    @ParameterizedTest
    @ValueSource(strings={"Daoer"})
    void getTest2(String Id) throws JsonProcessingException {
        List<ParkingLotConfiguration> data=repository.findParkingLotConfigurationByParkingType(Id);
        for (ParkingLotConfiguration cfg:data){
            System.out.println(cfg);
            System.out.println(cfg.getConfig());
        }

    }
}

