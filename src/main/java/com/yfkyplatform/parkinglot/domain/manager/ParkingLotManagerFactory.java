package com.yfkyplatform.parkinglot.domain.manager;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 停车场代理管理工厂
 *
 * @author Suhuyuan
 */
@Component
public class ParkingLotManagerFactory {
    private Map<String, ParkingLotManager> parkingLotManagerMap=new ConcurrentHashMap<>();

    public ParkingLotManagerFactory(List<ParkingLotManager> parkingLotManagerList){
        loadParkingLotManager(parkingLotManagerList);
    }

    /**
     * 加载停车场管理
     * @param parkingLotManagerList
     */
    public void loadParkingLotManager(List<ParkingLotManager> parkingLotManagerList){
        for (ParkingLotManager manager:parkingLotManagerList) {
            String managerSimpleName=manager.getClass().getSimpleName();
            int parkingLotManagerIndex=managerSimpleName.indexOf("ParkingLotManager");
            String managerName=parkingLotManagerIndex>0?managerSimpleName.substring(0,parkingLotManagerIndex):managerSimpleName;
            parkingLotManagerMap.put(managerName,manager);
        }
    }

    /**
     * 获取停车场管理
     * @param parkingLotManagerName
     * @return
     */
    public ParkingLotManager<?,?> manager(String parkingLotManagerName){
        if(StrUtil.isBlank(parkingLotManagerName)){
            throw new IllegalArgumentException("parkingLotManagerName 不能为空");
        }
        if(parkingLotManagerMap.containsKey(parkingLotManagerName)){
            return parkingLotManagerMap.get(parkingLotManagerName);
        }else {
            throw new NoSuchElementException(parkingLotManagerName+"不存在");
        }
    }
}
