package com.yfkyplatform.parkinglot.domain.manager;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglot.domain.repository.ParkingLotConfigurationRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 停车场管理
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotManager<T extends ParkingLotPod,Data extends ParkingLotConfiguration> {
    protected Map<String, T> parkingLotMap=new ConcurrentHashMap<>();

    protected ParkingLotConfigurationRepository cfgRepository;

    protected RedisTool redis;

    public ParkingLotManager(RedisTool redisTool,ParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
        redis= redisTool;
        this.cfgRepository=cfgRepository;
        load(loadData());
    }

    /**
     * 根据配置数据加载实例
     * @param data
     * @return
     */
    protected abstract T  load(Data data);

    /**
     * 加载配置数据
     * @return
     * @throws JsonProcessingException
     */
    protected  abstract List<Data> loadData() throws JsonProcessingException;

    /**
     * 基本参数配置
     * @param dataList
     */
    private void load(List<Data> dataList){
        for (Data item:dataList) {
            T parkingLot=load(item);
            parkingLotMap.put(item.getId(),parkingLot);
        }
    }

    /**
     * 获取停车场
     * @param parkingLotId
     * @return
     */
    public <T extends ParkingLotPod>T parkingLot(String parkingLotId){
        if(StrUtil.isBlank(parkingLotId)){
            throw new IllegalArgumentException("parkingLotId 不能为空");
        }

        return parkingLotMap.containsKey(parkingLotId)
                ?(T)parkingLotMap.get(parkingLotId)
                :null;
    }
}
