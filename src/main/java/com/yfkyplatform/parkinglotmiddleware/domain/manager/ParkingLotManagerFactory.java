package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 停车场代理管理工厂
 *
 * @author Suhuyuan
 */
@Component
public class ParkingLotManagerFactory {
    private final Map<String, ParkingLotManager> parkingLotManagerMap = new ConcurrentHashMap<>();

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

    /**
     * 获取支持的管理器列表
     * @return
     */
    public Set<String> getManagerSupport(){
        return parkingLotManagerMap.keySet();
    }

    /**
     * 获取配置文件
     *
     * @param parkingLotManagerName
     * @return
     */
    public List<ParkingLotConfiguration> getParkingLotConfiguration(@Nullable String parkingLotManagerName, @Nullable String parkingLotId) {
        if (!StrUtil.isBlank(parkingLotManagerName)) {
            return parkingLotManagerMap.get(parkingLotManagerName).configurationList(parkingLotId);
        } else {
            List dataList = new ArrayList();
            parkingLotManagerMap.values().forEach(manager -> {
                dataList.addAll(manager.configurationList(parkingLotId));
            });
            return dataList;
        }
    }

    /**
     * 健康检查
     *
     * @param parkingLotManagerName
     * @param parkingLotId
     * @return
     */
    public Map<String, Map<String, Boolean>> healthCheck(@Nullable String parkingLotManagerName, @Nullable String parkingLotId) {
        Map<String, Map<String, Boolean>> dataMap = new HashMap<>(100);
        if (!StrUtil.isBlank(parkingLotManagerName)) {
            ParkingLotManager manager = parkingLotManagerMap.get(parkingLotManagerName);
            dataMap.put(parkingLotManagerName, manager.parkingLotHealthCheck(parkingLotId));
        } else {
            parkingLotManagerMap.forEach((name, manager) -> {
                dataMap.put(name, manager.parkingLotHealthCheck(parkingLotId));
            });
        }
        return dataMap;
    }
}
