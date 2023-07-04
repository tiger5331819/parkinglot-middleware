package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

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
public class DaoerParkingLotManager extends ParkingLotManager<DaoerParkingLot, DaoerParkingLotConfiguration> {

    public DaoerParkingLotManager(ParkingLotManagerInfrastructure infrastructure) {
        super(infrastructure, "Daoer");
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected DaoerParkingLot load(String parkingLotId) {
        ParkingLotConfiguration<DaoerConfiguration> cfg = cfgRepository().findParkingLotConfigurationByParkingTypeAndParkingLotId(managerType, parkingLotId);
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        DaoerConfiguration daoerCfg = cfg.getConfig();
        DaoerParkingLotConfiguration parkingLotConfiguration = new DaoerParkingLotConfiguration(cfg.getParkingLotId(), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), cfg.getDescription(), daoerCfg.getImgUrl(), daoerCfg.getBackTrack());
        return new DaoerParkingLot(parkingLotConfiguration, redis);
    }

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    @Override
    protected List<DaoerParkingLot> load() {
        List<DaoerParkingLot> dataList = new ArrayList<>();
        List<ParkingLotConfiguration> cfgList = cfgRepository().findParkingLotConfigurationByParkingType(managerType);

        for (ParkingLotConfiguration<DaoerConfiguration> item : cfgList) {
            DaoerConfiguration daoerCfg = item.getConfig();
            DaoerParkingLotConfiguration parkingLotConfiguration = new DaoerParkingLotConfiguration(item.getParkingLotId(), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), item.getDescription(), daoerCfg.getImgUrl(), daoerCfg.getBackTrack());
            dataList.add(new DaoerParkingLot(parkingLotConfiguration, redis));
        }
        return dataList;
    }

    /**
     * 检查配置数据
     *
     * @param daoerParkingLotConfiguration
     * @return
     */
    @Override
    protected void dataCheck(DaoerParkingLotConfiguration daoerParkingLotConfiguration) {
        if(daoerParkingLotConfiguration instanceof DaoerParkingLotConfiguration){
        }else{
            throw new IllegalArgumentException("不是道尔停车场配置文件");
        }
    }

    /**
     * 保存配置数据
     *
     * @param daoerParkingLotConfiguration
     * @return
     */
    @Override
    protected Boolean SaveData(DaoerParkingLotConfiguration daoerParkingLotConfiguration) {
        DaoerConfiguration cfg = new DaoerConfiguration();
        cfg.setAppName(daoerParkingLotConfiguration.getAppName());
        cfg.setParkId(daoerParkingLotConfiguration.getParkId());
        cfg.setBaseUrl(daoerParkingLotConfiguration.getBaseUrl());

        ParkingLotConfiguration data = new ParkingLotConfiguration();
        data.setParkingLotId(daoerParkingLotConfiguration.getId());
        data.setParkingType("Daoer");
        data.setDescription(daoerParkingLotConfiguration.getDescription());
        data.setConfig(cfg);

        ParkingLotConfiguration<DaoerConfiguration> result = cfgRepository().save(data);
        return ObjectUtil.isNotNull(result);
    }
}
