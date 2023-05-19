package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglotmiddleware.universal.TestBox;
import lombok.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 停车场管理核心
 *
 * @author Suhuyuan
 */
@Value
@Component
public class ParkingLotManagerInfrastructure {

    private List<IParkingLotConfigurationRepository> cfgRepositoryList;

    private RedisTool redis;

    private TestBox testBox;

    public ParkingLotManagerInfrastructure(List<IParkingLotConfigurationRepository> cfgRepositoryList, RedisTool redis, TestBox testBox) {
        this.redis = redis;
        this.cfgRepositoryList = cfgRepositoryList;
        this.testBox = testBox;
    }

    public IParkingLotConfigurationRepository getCfgRepository() {
        if (testBox.checkTest()) {
            Optional<IParkingLotConfigurationRepository> cfgOptional = cfgRepositoryList.stream().filter(item -> item.getClass().getSimpleName().contains("ParkingLotConfigurationRepositoryByConfiguration")).findFirst();
            return cfgOptional.get();
        } else {
            Optional<IParkingLotConfigurationRepository> cfgOptional = cfgRepositoryList.stream().filter(item -> item.getClass().isAnnotationPresent(Primary.class)).findFirst();
            if (cfgOptional.isPresent()) {
                return cfgOptional.get();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
