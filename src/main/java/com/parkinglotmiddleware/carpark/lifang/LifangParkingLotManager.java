package com.parkinglotmiddleware.carpark.lifang;

import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerInfrastructure;
import com.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.parkinglotmiddleware.domain.repository.model.LifangConfiguration;
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
public class LifangParkingLotManager extends ParkingLotManager<LifangParkingLot, LifangParkingLotConfiguration> {

    public LifangParkingLotManager(ParkingLotManagerInfrastructure infrastructure) {
        super(infrastructure, "Lifang");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected LifangParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<LifangConfiguration> cfg = cfgRepository().findParkingLotConfigurationByParkingTypeAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        LifangConfiguration lifangCfg = cfg.getConfig();
        LifangParkingLotConfiguration parkingLotConfiguration = new LifangParkingLotConfiguration(cfg.getId(), lifangCfg.getSecret(), lifangCfg.getBaseUrl(), cfg.getDescription());
        return new LifangParkingLot(parkingLotConfiguration, infrastructure.getRedis());
    }

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    @Override
    protected List<LifangParkingLot> load() {
        List<LifangParkingLot> dataList = new ArrayList<>();
        List<ParkingLotConfiguration> cfgList = cfgRepository().findParkingLotConfigurationByParkingType(managerType);

        for (ParkingLotConfiguration<LifangConfiguration> item : cfgList) {
            LifangConfiguration lifangCfg = item.getConfig();
            LifangParkingLotConfiguration parkingLotConfiguration = new LifangParkingLotConfiguration(item.getId(), lifangCfg.getSecret(), lifangCfg.getBaseUrl(), item.getDescription());
            dataList.add(new LifangParkingLot(parkingLotConfiguration, infrastructure.getRedis()));
        }
        return dataList;
    }

    /**
     * 检查配置数据
     *
     * @param lifangParkingLotConfiguration
     * @return
     */
    @Override
    protected void dataCheck(LifangParkingLotConfiguration lifangParkingLotConfiguration) {
        if (lifangParkingLotConfiguration instanceof LifangParkingLotConfiguration) {
        } else {
            throw new IllegalArgumentException("不是立方停车场配置文件");
        }
    }

    /**
     * 保存配置数据
     *
     * @param LifangParkingLotConfiguration
     * @return
     */
    @Override
    protected Boolean SaveData(LifangParkingLotConfiguration LifangParkingLotConfiguration) {
        LifangConfiguration cfg = new LifangConfiguration();
        cfg.setSecret(LifangParkingLotConfiguration.getSecret());
        cfg.setBaseUrl(LifangParkingLotConfiguration.getBaseUrl());

        ParkingLotConfiguration data = new ParkingLotConfiguration();
        data.setId(LifangParkingLotConfiguration.getId());
        data.setManagerType("Lifang");
        data.setDescription(LifangParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<DaoerConfiguration> result = cfgRepository().save(data);
        return ObjectUtil.isNotNull(result);
    }
}
