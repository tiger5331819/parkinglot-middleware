package com.yfkyplatform.parkinglot.daoerparkinglot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.DaoerClient;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglot.domain.repository.ParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglot.domain.repository.model.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglot.domain.repository.model.ParkingLotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 道尔停车场代理管理
 *
 * @author Suhuyuan
 */
@Component
public class DaoerParkingLotManager extends ParkingLotManager<DaoerClient,DaoerParkingLotInfo> {

    public DaoerParkingLotManager(RedisTool redisTool,ParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        super(redisTool,cfgRepository);
    }

    @Override
    protected DaoerClient load(DaoerParkingLotInfo daoerParkingLotInfo) {
        return new DaoerClient(daoerParkingLotInfo.getAppName(),daoerParkingLotInfo.getParkId(),daoerParkingLotInfo.getBaseUrl(),redis);
    }

    @Override
    protected List<DaoerParkingLotInfo> loadData() throws JsonProcessingException {
        List<DaoerParkingLotInfo> dataList=new ArrayList<>();
        List<ParkingLotConfiguration<DaoerParkingLotConfiguration>> cfgList= cfgRepository.findParkingLotConfigurationByParkingType("Daoer");

        for (ParkingLotConfiguration<DaoerParkingLotConfiguration> item: cfgList) {
            DaoerParkingLotConfiguration cfg= item.getConfig(DaoerParkingLotConfiguration.class);
            dataList.add(new DaoerParkingLotInfo(item.getParkingLotId(), cfg.getAppName(), cfg.getParkId(),cfg.getBaseUrl()));
        }
        return dataList;
    }
}
