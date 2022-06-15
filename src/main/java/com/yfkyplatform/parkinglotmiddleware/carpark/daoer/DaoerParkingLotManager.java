package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 道尔停车场代理管理
 *
 * @author Suhuyuan
 */
@Component
public class DaoerParkingLotManager extends ParkingLotManager<DaoerParkingLot, DaoerParkingLotConfiguration> {

    public DaoerParkingLotManager(RedisTool redisTool, @Qualifier("parkingLotConfigurationRepositoryByRedis") IParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        super(redisTool,cfgRepository);
    }

    @Override
    protected DaoerParkingLot load(DaoerParkingLotConfiguration daoerParkingLotInfo) {
        return new DaoerParkingLot(daoerParkingLotInfo,redis);
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

    @Override
    protected List<DaoerParkingLotConfiguration> loadData() throws JsonProcessingException {
        List<DaoerParkingLotConfiguration> dataList=new ArrayList<>();
        List<ParkingLotConfiguration> cfgList= cfgRepository.findParkingLotConfigurationByParkingType("Daoer");

        for (ParkingLotConfiguration<DaoerConfiguration> item: cfgList) {
            DaoerConfiguration cfg= item.getConfig();
            dataList.add(new DaoerParkingLotConfiguration(item.getParkingLotId(), cfg.getAppName(), cfg.getParkId(),cfg.getBaseUrl()));
        }
        return dataList;
    }

    /**
     * 保存配置数据
     *
     * @param daoerParkingLotConfiguration
     * @return
     */
    @Override
    protected boolean SaveData(DaoerParkingLotConfiguration daoerParkingLotConfiguration){
        ParkingLotConfiguration data=new ParkingLotConfiguration(daoerParkingLotConfiguration.getId(),"Daoer");
        data.setConfig(new DaoerConfiguration(daoerParkingLotConfiguration.getAppName(), daoerParkingLotConfiguration.getParkId(), daoerParkingLotConfiguration.getBaseUrl()));
        ParkingLotConfiguration<DaoerConfiguration> result= cfgRepository.save(data);
        return ObjectUtil.isNotNull(result);
    }

    /**
     * 通过车场Id获取停车场
     *
     * @param parkingId
     * @return
     */
    @Override
    public <T extends ParkingLotPod> T parkingLotFromPark(String parkingId) {
        for(DaoerParkingLot item:parkingLotMap.values()){
            DaoerParkingLotConfiguration cfg=item.configuration();
            if(cfg.getParkId().equals(parkingId)){
                return (T) item;
            }
        }
        throw new NoSuchElementException("找不到对应的停车场");
    }
}
