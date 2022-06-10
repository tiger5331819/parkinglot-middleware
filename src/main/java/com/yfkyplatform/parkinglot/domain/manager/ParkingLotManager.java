package com.yfkyplatform.parkinglot.domain.manager;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglot.domain.repository.IParkingLotConfigurationRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 停车场管理
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotManager<T extends ParkingLotPod,Data extends ParkingLotConfiguration> {
    protected Map<String, T> parkingLotMap=new ConcurrentHashMap<>();

    protected IParkingLotConfigurationRepository cfgRepository;

    protected RedisTool redis;

    public ParkingLotManager(RedisTool redisTool, IParkingLotConfigurationRepository cfgRepository) throws JsonProcessingException {
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
     * 保存配置数据
     * @param data
     * @return
     */
    protected  abstract boolean SaveData(Data data) throws JsonProcessingException;

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
     * 添加停车场
     * @param data 停车场配置数据
     * @return
     * @throws JsonProcessingException
     */
    public boolean addParkingLot(Data data) throws JsonProcessingException {
        if(SaveData(data)){
            T parkingLot=load(data);
            parkingLotMap.put(data.getId(),parkingLot);
            return true;
        } else{
          return false;
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

        if(parkingLotMap.containsKey(parkingLotId)){
            return (T)parkingLotMap.get(parkingLotId);
        }else {
            throw new NoSuchElementException(parkingLotId+"不存在");
        }
    }

    /**
     * 通过车场Id获取停车场
     * @param parkingId
     * @return
     */
    public abstract  <T extends ParkingLotPod>T parkingLotFromPark(String parkingId);
}
