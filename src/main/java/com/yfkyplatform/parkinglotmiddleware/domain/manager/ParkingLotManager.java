package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;

import java.util.*;

/**
 * 停车场管理
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotManager<T extends ParkingLotPod,Data extends ParkingLotConfiguration> {

    protected IParkingLotConfigurationRepository cfgRepository;

    protected RedisTool redis;

    public ParkingLotManager(RedisTool redisTool, IParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        redis = redisTool;
        this.cfgRepository = cfgRepository;
    }

    /**
     * 根据配置数据加载实例
     *
     * @param parkingLotId
     * @return
     */
    protected abstract T load(String parkingLotId);

    /**
     * 根据配置数据加载所有实例
     *
     * @return
     */
    protected abstract List<T> load();

    /**
     * 检查配置数据
     *
     * @param data
     * @return
     */
    protected abstract void dataCheck(Data data);

    /**
     * 保存配置数据
     * @param data
     * @return
     */
    protected  abstract boolean SaveData(Data data) throws JsonProcessingException;

    /**
     * 添加停车场
     * @param data 停车场配置数据
     * @return
     * @throws JsonProcessingException
     */
    public boolean addParkingLot(Data data) throws JsonProcessingException {
        dataCheck(data);
        return SaveData(data);
    }

    /**
     * 获取停车场
     *
     * @param parkingLotId
     * @return
     */
    public <T extends ParkingLotPod> T parkingLot(String parkingLotId) {
        if (StrUtil.isBlank(parkingLotId)) {
            throw new IllegalArgumentException("parkingLotId 不能为空");
        }

        T parkingLot = (T) load(parkingLotId);
        if (!ObjectUtil.isNull(parkingLot)) {
            return parkingLot;
        } else {
            throw new NoSuchElementException(parkingLotId + "不存在");
        }
    }

    /**
     * 获取停车场配置信息
     * @param parkingLotId
     * @return
     */
    public List<ParkingLotConfiguration> configurationList(String parkingLotId){
        List cfgList=new ArrayList();

        if(!StrUtil.isBlank(parkingLotId)) {
            cfgList.add(load(parkingLotId).configuration());
        } else {
            load().forEach(item -> cfgList.add(item.configuration()));
        }
        return cfgList;
    }

    /**
     * 获取停车场健康检查结果
     * @param parkingLotId
     * @return
     */
    public Map<String,Boolean> parkingLotHealthCheck(String parkingLotId) {
        Map healthCheckMap=new HashMap(100);
        if(!StrUtil.isBlank(parkingLotId)) {
            T parkingLot=load(parkingLotId);
            healthCheckMap.put(parkingLot.Id(),parkingLot.healthCheck());
        } else {
            load().forEach(item -> healthCheckMap.put(item.Id(),item.healthCheck()));
        }
        return healthCheckMap;
    }
}
