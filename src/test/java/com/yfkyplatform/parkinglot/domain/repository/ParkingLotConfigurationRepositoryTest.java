package com.yfkyplatform.parkinglot.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglot.domain.repository.model.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglot.domain.repository.model.ParkingLotConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ParkingLotConfigurationRepositoryTest {

    @Autowired
    private ParkingLotConfigurationRepository repository;

    @Test
    void addTest() throws JsonProcessingException {
        ParkingLotConfiguration<DaoerParkingLotConfiguration> data=new ParkingLotConfiguration("DaoerTest","Daoer");
        data.setParkingLotId("DaoerTest");
        data.setConfig(new DaoerParkingLotConfiguration("a909fb0eb10240979b2b374273bf6342","X24400000001","https://parklot.q-parking.com"));
        repository.save(data);
    }

    @ParameterizedTest
    @ValueSource(strings={"DaoerTest"})
    void getTest(String Id){
        ParkingLotConfiguration<DaoerParkingLotConfiguration> data=repository.findParkingLotConfigurationByParkingLotId(Id);
        System.out.println(data);
    }

    @ParameterizedTest
    @ValueSource(strings={"Daoer"})
    void getTest2(String Id){
        List<ParkingLotConfiguration<DaoerParkingLotConfiguration>> data=repository.findParkingLotConfigurationByParkingType(Id);
        System.out.println(data);
    }
}

