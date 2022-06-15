package com.yfkyplatform.parkinglotmiddleware.api.dubbo.manager;

import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
public class ManagerServiceTest {
    @DubboReference
    IManagerService managerService;

    @ParameterizedTest
    @CsvSource({"Daoer,科盈测试","Daoer,铜陵","Daoer,",
            ",科盈测试",",铜陵",","})
    void parkingMangerConfigurationTest(String parkingLotManagerName, String parkingLotId){
        List<ParkingLotCfgRpcResp> data=managerService.parkingMangerConfiguration(parkingLotManagerName, parkingLotId);
        assertNotNull(data);
        assertNotEquals(0,data.size());
        data.forEach(item->{
            System.out.println(item);
        });
    }
}