package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.HongmenConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 道尔停车场代理管理
 *
 * @author Suhuyuan
 */
@Component
public class HongmenParkingLotManager extends ParkingLotManager<HongmenParkingLot, HongmenParkingLotConfiguration> {

    public HongmenParkingLotManager(RedisTool redisTool, @Qualifier("parkingLotConfigurationRepositoryByConfiguration") IParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        super(redisTool, cfgRepository, "Hongmen");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected HongmenParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<HongmenConfiguration> cfg = cfgRepository.findParkingLotConfigurationByParkingTypeAndAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        HongmenConfiguration hongmenCfg = cfg.getConfig();
        HongmenParkingLotConfiguration parkingLotConfiguration = new HongmenParkingLotConfiguration(cfg.getParkingLotId(), hongmenCfg.getAppId(), hongmenCfg.getSecret(), hongmenCfg.getBaseUrl(), cfg.getDescription());
        return new HongmenParkingLot(parkingLotConfiguration, redis);
    }

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    @Override
    protected List<HongmenParkingLot> load() {
        List<HongmenParkingLot> dataList = new ArrayList<>();
        List<ParkingLotConfiguration> cfgList = cfgRepository.findParkingLotConfigurationByParkingType(managerType);

        for (ParkingLotConfiguration<HongmenConfiguration> item : cfgList) {
            HongmenConfiguration hongmen = item.getConfig();
            HongmenParkingLotConfiguration parkingLotConfiguration = new HongmenParkingLotConfiguration(item.getParkingLotId(), hongmen.getAppId(), hongmen.getSecret(), hongmen.getBaseUrl(), item.getDescription());
            dataList.add(new HongmenParkingLot(parkingLotConfiguration, redis));
        }
        return dataList;
    }

    /**
     * 检查配置数据
     *
     * @param hongmenParkingLotConfiguration
     * @return
     */
    @Override
    protected void dataCheck(HongmenParkingLotConfiguration hongmenParkingLotConfiguration) {
        if (hongmenParkingLotConfiguration instanceof HongmenParkingLotConfiguration) {
        } else {
            throw new IllegalArgumentException("不是立方停车场配置文件");
        }
    }

    /**
     * 保存配置数据
     *
     * @param HongmenParkingLotConfiguration
     * @return
     */
    @Override
    protected Boolean SaveData(HongmenParkingLotConfiguration HongmenParkingLotConfiguration) {
        HongmenConfiguration cfg = new HongmenConfiguration();
        cfg.setAppId(HongmenParkingLotConfiguration.getAppId());
        cfg.setSecret(HongmenParkingLotConfiguration.getSecret());
        cfg.setBaseUrl(HongmenParkingLotConfiguration.getBaseUrl());

        ParkingLotConfiguration data = new ParkingLotConfiguration();
        data.setParkingLotId(HongmenParkingLotConfiguration.getId());
        data.setParkingType("Hongmen");
        data.setDescription(HongmenParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<HongmenConfiguration> result = cfgRepository.save(data);
        return ObjectUtil.isNotNull(result);
    }
}
