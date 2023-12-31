package com.parkinglotmiddleware.api.dubbo.exposer.manager;

import com.parkinglotmiddleware.api.dubbo.service.manager.configuration.IManagerConfigurationService;
import com.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
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
     * @param imgUrl  道尔图片访问地址
     * @return
     */
    @Override
    public Boolean addDaoerCongfiguration(String id, String appName, String parkId, String baseUrl, String description, String imgUrl) {
        ParkingLotManager daoerManager = factory.manager("Daoer");
        DaoerParkingLotConfiguration cfg = new DaoerParkingLotConfiguration(id, appName, parkId, baseUrl, description, imgUrl, null);

        return daoerManager.addParkingLot(cfg);
    }
}
