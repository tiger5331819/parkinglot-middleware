package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerInfrastructure;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
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
        super(infrastructure, "Daoer");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected JieShunParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<DaoerConfiguration> cfg = cfgRepository().findParkingLotConfigurationByParkingTypeAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        DaoerConfiguration daoerCfg = cfg.getConfig();
        JieShunParkingLotConfiguration parkingLotConfiguration = new JieShunParkingLotConfiguration(cfg.getParkingLotId(), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), cfg.getDescription(), daoerCfg.getImgUrl(), daoerCfg.getBackTrack());
        return new JieShunParkingLot(parkingLotConfiguration, redis);
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

        for (ParkingLotConfiguration<DaoerConfiguration> item : cfgList) {
            DaoerConfiguration daoerCfg = item.getConfig();
            JieShunParkingLotConfiguration parkingLotConfiguration = new JieShunParkingLotConfiguration(item.getParkingLotId(), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), item.getDescription(), daoerCfg.getImgUrl(), daoerCfg.getBackTrack());
            dataList.add(new JieShunParkingLot(parkingLotConfiguration, redis));
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
        cfg.setAppName(jieShunParkingLotConfiguration.getAppName());
        cfg.setParkId(jieShunParkingLotConfiguration.getParkId());
        cfg.setBaseUrl(jieShunParkingLotConfiguration.getBaseUrl());

        ParkingLotConfiguration data = new ParkingLotConfiguration();
        data.setParkingLotId(jieShunParkingLotConfiguration.getId());
        data.setParkingType("Daoer");
        data.setDescription(jieShunParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<DaoerConfiguration> result = cfgRepository().save(data);
        return ObjectUtil.isNotNull(result);
    }
}
