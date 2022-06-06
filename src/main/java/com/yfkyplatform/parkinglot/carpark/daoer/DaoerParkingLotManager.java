package com.yfkyplatform.parkinglot.carpark.daoer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglot.domain.repository.ParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglot.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglot.domain.repository.model.ParkingLotConfiguration;
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

    public DaoerParkingLotManager(RedisTool redisTool,ParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        super(redisTool,cfgRepository);
    }

    @Override
    protected DaoerParkingLot load(DaoerParkingLotConfiguration daoerParkingLotInfo) {
        return new DaoerParkingLot(daoerParkingLotInfo,redis);
    }

    @Override
    protected List<DaoerParkingLotConfiguration> loadData() throws JsonProcessingException {
        List<DaoerParkingLotConfiguration> dataList=new ArrayList<>();
        List<ParkingLotConfiguration> cfgList= cfgRepository.findParkingLotConfigurationByParkingType("Daoer");

        for (ParkingLotConfiguration<DaoerConfiguration> item: cfgList) {
            DaoerConfiguration cfg= item.getConfig(DaoerConfiguration.class);
            dataList.add(new DaoerParkingLotConfiguration(item.getParkingLotId(), cfg.getAppName(), cfg.getParkId(),cfg.getBaseUrl()));
        }
        return dataList;
    }
}
