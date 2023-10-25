package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerInfrastructure;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.LifangConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 道尔停车场代理管理
 *
 * @author Suhuyuan
 */
@Component
public class JieShunParkingLotManager extends ParkingLotManager<JieShunParkingLot, JieShunParkingLotConfiguration> {

    public JieShunParkingLotManager(ParkingLotManagerInfrastructure infrastructure) {
        super(infrastructure, "JieShun");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected JieShunParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<LifangConfiguration> cfg = cfgRepository().findParkingLotConfigurationByParkingTypeAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        LifangConfiguration jieShunCfg = cfg.getConfig();
        JieShunParkingLotConfiguration parkingLotConfiguration = new JieShunParkingLotConfiguration(cfg.getId(), jieShunCfg.getSecret(), jieShunCfg.getBaseUrl(), cfg.getDescription());
        return new JieShunParkingLot(parkingLotConfiguration, infrastructure.getRedis());
    }

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    @Override
    protected List<JieShunParkingLot> load() {
        List<JieShunParkingLot> dataList = new ArrayList<>();
        List<ParkingLotConfiguration> cfgList = cfgRepository().findParkingLotConfigurationByParkingType(managerType);

        for (ParkingLotConfiguration<LifangConfiguration> item : cfgList) {
            LifangConfiguration jieShunCfg = item.getConfig();
            JieShunParkingLotConfiguration parkingLotConfiguration = new JieShunParkingLotConfiguration(item.getId(), jieShunCfg.getSecret(), jieShunCfg.getBaseUrl(), item.getDescription());
            dataList.add(new JieShunParkingLot(parkingLotConfiguration, infrastructure.getRedis()));
        }
        return dataList;
    }

    /**
     * 检查配置数据
     *
     * @param jieShunParkingLotConfiguration
     * @return
     */
    @Override
    protected void dataCheck(JieShunParkingLotConfiguration jieShunParkingLotConfiguration) {
        if (jieShunParkingLotConfiguration instanceof JieShunParkingLotConfiguration) {
        } else {
            throw new IllegalArgumentException("不是道尔停车场配置文件");
        }
    }

    /**
     * 保存配置数据
     *
     * @param jieShunParkingLotConfiguration
     * @return
     */
    @Override
    protected Boolean SaveData(JieShunParkingLotConfiguration jieShunParkingLotConfiguration) {
        DaoerConfiguration cfg = new DaoerConfiguration();
        cfg.setBaseUrl(jieShunParkingLotConfiguration.getBaseUrl());

        ParkingLotConfiguration data = new ParkingLotConfiguration();
        data.setId(jieShunParkingLotConfiguration.getId());
        data.setManagerType("Daoer");
        data.setDescription(jieShunParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<DaoerConfiguration> result = cfgRepository().save(data);
        return ObjectUtil.isNotNull(result);
    }
}
