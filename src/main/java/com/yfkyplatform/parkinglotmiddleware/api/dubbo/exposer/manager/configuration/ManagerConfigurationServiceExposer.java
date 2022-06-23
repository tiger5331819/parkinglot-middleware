package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.manager.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyplatform.parkinglotmiddleware.api.manager.configuration.IManagerConfigurationService;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * 管理服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class ManagerConfigurationServiceExposer implements IManagerConfigurationService {

    private final ParkingLotManagerFactory factory;

    public ManagerConfigurationServiceExposer(ParkingLotManagerFactory factory) {
        this.factory = factory;
    }


    /**
     * 添加道尔停车场配置文件
     *
     * @param id      停车场Id
     * @param appName 道尔 appName
     * @param parkId  道尔 parkId
     * @param baseUrl 道尔基础访问地址
     * @return
     */
    @Override
    public Boolean addDaoerCongfiguration(Long id, String appName, String parkId, String baseUrl, String description) {
        ParkingLotManager daoerManager = factory.manager("Daoer");
        DaoerParkingLotConfiguration cfg = new DaoerParkingLotConfiguration(id, appName, parkId, baseUrl, description);

        try {
            return daoerManager.addParkingLot(cfg);
        } catch (JsonProcessingException ex) {
            throw new ExposerException(-1, "道尔配置文件不正确", ex);
        }
    }
}
