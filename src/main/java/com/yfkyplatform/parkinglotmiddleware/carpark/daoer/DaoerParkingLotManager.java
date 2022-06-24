package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
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
public class DaoerParkingLotManager extends ParkingLotManager<DaoerParkingLot, DaoerParkingLotConfiguration> {

    public DaoerParkingLotManager(RedisTool redisTool, @Qualifier("parkingLotConfigurationRepositoryByRedis") IParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        super(redisTool, cfgRepository);
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    @Override
    protected DaoerParkingLot load(Long parkingLotId) {
        ParkingLotConfiguration<DaoerConfiguration> cfg = cfgRepository.findParkingLotConfigurationByParkingTypeAndAndParkingLotId("Daoer", parkingLotId.toString());
        if (ObjectUtil.isNull(cfg)) {
            return null;
        }
        DaoerConfiguration daoerCfg = cfg.getConfig();
        DaoerParkingLotConfiguration parkingLotConfiguration = new DaoerParkingLotConfiguration(Long.valueOf(cfg.getParkingLotId()), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), cfg.getDescription());
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
        List<ParkingLotConfiguration> cfgList = cfgRepository.findParkingLotConfigurationByParkingType("Daoer");

        for (ParkingLotConfiguration<DaoerConfiguration> item : cfgList) {
            DaoerConfiguration daoerCfg = item.getConfig();
            DaoerParkingLotConfiguration parkingLotConfiguration = new DaoerParkingLotConfiguration(Long.valueOf(item.getParkingLotId()), daoerCfg.getAppName(), daoerCfg.getParkId(), daoerCfg.getBaseUrl(), item.getDescription());
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
            return;
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
    protected boolean SaveData(DaoerParkingLotConfiguration daoerParkingLotConfiguration){
        ParkingLotConfiguration data = new ParkingLotConfiguration(daoerParkingLotConfiguration.getId(), "Daoer", daoerParkingLotConfiguration.getDescription());
        data.setConfig(new DaoerConfiguration(daoerParkingLotConfiguration.getAppName(), daoerParkingLotConfiguration.getParkId(), daoerParkingLotConfiguration.getBaseUrl()));
        ParkingLotConfiguration<DaoerConfiguration> result= cfgRepository.save(data);
        return ObjectUtil.isNotNull(result);
    }
}
