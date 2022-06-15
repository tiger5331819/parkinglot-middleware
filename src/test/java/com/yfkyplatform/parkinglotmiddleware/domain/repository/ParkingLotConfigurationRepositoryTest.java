package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ParkingLotConfigurationRepositoryTest {

    @Qualifier("parkingLotConfigurationRepositoryByRedis")
    @Autowired
    private IParkingLotConfigurationRepository repository;

    @Test
    void addTest() throws JsonProcessingException {
        ParkingLotConfiguration<DaoerConfiguration> data=new ParkingLotConfiguration("DaoerTest","Daoer");
        data.setConfig(new DaoerConfiguration("a909fb0eb10240979b2b374273bf6342","X24400000001","https://parklot.q-parking.com"));
        repository.save(data);
    }

    @ParameterizedTest
    @ValueSource(strings={"DaoerTest"})
    void getTest(String Id){
        ParkingLotConfiguration<DaoerConfiguration> data=repository.findById(Id).get();
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

