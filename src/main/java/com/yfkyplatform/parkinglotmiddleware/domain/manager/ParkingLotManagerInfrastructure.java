package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglotmiddleware.universal.extension.IExtensionFuction;
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

    private IExtensionFuction extensionFuction;

    public ParkingLotManagerInfrastructure(List<IParkingLotConfigurationRepository> cfgRepositoryList, RedisTool redis, IExtensionFuction extensionFuction, List<IParkingLotConfigurationRepository> cfgRepositoryList1, IExtensionFuction extensionFuction1) {
        this.redis = redis;
        this.cfgRepositoryList = cfgRepositoryList1;
        this.extensionFuction = extensionFuction1;
    }

    public IParkingLotConfigurationRepository getCfgRepository() {
        boolean token = false;
        try {
            token = extensionFuction.getToken();
        } catch (Exception ex) {
            token = false;
        }

        if (token) {
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
