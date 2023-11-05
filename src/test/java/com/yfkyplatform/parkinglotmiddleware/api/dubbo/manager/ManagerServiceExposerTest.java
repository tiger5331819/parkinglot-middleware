package com.parkinglotmiddleware.api.dubbo.service.dubbo.manager;

import com.parkinglotmiddleware.api.dubbo.service.manager.IManagerService;
import com.parkinglotmiddleware.api.dubbo.service.manager.response.ParkingLotCfgRpcResp;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class ManagerServiceExposerTest {
    @DubboReference
    IManagerService managerService;

    @ParameterizedTest
    @CsvSource({"Daoer,科盈测试", "Daoer,铜陵", "Daoer,",
            ",科盈测试", ",铜陵", ","})
    void parkingMangerConfigurationTest(Integer parkingLotManagerName, String parkingLotId) {
        List<ParkingLotCfgRpcResp> data = managerService.parkingMangerConfiguration(parkingLotManagerName, parkingLotId);
        assertNotNull(data);
        assertNotEquals(0, data.size());
        data.forEach(item -> {
            System.out.println(item);
        });
    }
}