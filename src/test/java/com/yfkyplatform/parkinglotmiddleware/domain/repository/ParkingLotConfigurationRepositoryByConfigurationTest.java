package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ParkingLotConfigurationRepositoryByConfigurationTest {
    @Autowired
    private ParkingLotConfigurationRepositoryByConfiguration repository;

    @ParameterizedTest
    @ValueSource(strings = {"Daoer"})
    void getTest2(String Id) {
        List<ParkingLotConfiguration> data = repository.findParkingLotConfigurationByParkingType(Id);
        for (ParkingLotConfiguration cfg : data) {
            System.out.println(cfg);
            System.out.println(cfg.getConfig());
        }

    }
}