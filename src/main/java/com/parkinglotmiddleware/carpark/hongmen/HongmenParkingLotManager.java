package com.parkinglotmiddleware.carpark.hongmen;

import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerInfrastructure;
import com.parkinglotmiddleware.domain.repository.model.HongmenConfiguration;
import com.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
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

    public HongmenParkingLotManager(ParkingLotManagerInfrastructure infrastructure) {
        super(infrastructure, "Hongmen");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected HongmenParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<HongmenConfiguration> cfg = cfgRepository().findParkingLotConfigurationByParkingTypeAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        HongmenConfiguration hongmenCfg = cfg.getConfig();
        HongmenParkingLotConfiguration parkingLotConfiguration = new HongmenParkingLotConfiguration(cfg.getId(), hongmenCfg.getAppId(), hongmenCfg.getSecret(), hongmenCfg.getBaseUrl(), cfg.getDescription());
        return new HongmenParkingLot(parkingLotConfiguration, infrastructure.getRedis());
    }

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    @Override
    protected List<HongmenParkingLot> load() {
        List<HongmenParkingLot> dataList = new ArrayList<>();
        List<ParkingLotConfiguration> cfgList = cfgRepository().findParkingLotConfigurationByParkingType(managerType);

        for (ParkingLotConfiguration<HongmenConfiguration> item : cfgList) {
            HongmenConfiguration hongmen = item.getConfig();
            HongmenParkingLotConfiguration parkingLotConfiguration = new HongmenParkingLotConfiguration(item.getId(), hongmen.getAppId(), hongmen.getSecret(), hongmen.getBaseUrl(), item.getDescription());
            dataList.add(new HongmenParkingLot(parkingLotConfiguration, infrastructure.getRedis()));
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
        data.setId(HongmenParkingLotConfiguration.getId());
        data.setManagerType("Hongmen");
        data.setDescription(HongmenParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<HongmenConfiguration> result = cfgRepository().save(data);
        return ObjectUtil.isNotNull(result);
    }
}
