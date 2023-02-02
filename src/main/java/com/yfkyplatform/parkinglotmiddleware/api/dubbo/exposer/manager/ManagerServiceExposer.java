package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.manager;


import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.DaoerParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.LifangParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.LifangParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 管理服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class ManagerServiceExposer implements IManagerService {

    private final ParkingLotManagerFactory factory;

    public ManagerServiceExposer(ParkingLotManagerFactory factory) {
        this.factory = factory;
    }


    /**
     * 获取支持的管理器列表
     *
     * @return
     */
    @Override
    public Set<Integer> managerSupport() {
        Set<String> managerSet = factory.getManagerSupport();
        return managerSet.stream().map(item -> ParkingLotManagerEnum.valueOf(item).getCode()).collect(Collectors.toSet());
    }

    /**
     * 获取配置文件
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<ParkingLotCfgRpcResp> parkingMangerConfiguration(@Nullable Integer parkingLotManagerCode, @Nullable String parkingLotId) {

        ParkingLotManagerEnum parkingLotManagerEnum = ParkingLotManagerEnum.fromCode(parkingLotManagerCode);
        List<ParkingLotConfiguration> cfgList;
        if (ObjectUtil.isNotNull(parkingLotManagerEnum)) {
            cfgList = factory.getParkingLotConfiguration(parkingLotManagerEnum.getName(), parkingLotId);
        } else {
            cfgList = factory.getParkingLotConfiguration(null, parkingLotId);
        }

        if (cfgList.isEmpty()) {
            throw new NoSuchElementException(parkingLotManagerCode + ":" + parkingLotId + "不存在");
        }

        List<ParkingLotCfgRpcResp> result = new ArrayList<>();
        cfgList.forEach(item -> {
            if ((item instanceof DaoerParkingLotConfiguration)) {
                DaoerParkingLotCfgRpcResp data = new DaoerParkingLotCfgRpcResp();
                data.setAppName(((DaoerParkingLotConfiguration) item).getAppName());
                data.setParkId(((DaoerParkingLotConfiguration) item).getParkId());
                data.setBaseUrl(((DaoerParkingLotConfiguration) item).getBaseUrl());

                data.setId(item.getId());
                data.setDescription(item.getDescription());
                data.setManagerType(ParkingLotManagerEnum.fromMessage(item.getManagerType()).getCode());
                result.add(data);
            }
            if ((item instanceof LifangParkingLotConfiguration)) {
                LifangParkingLotCfgRpcResp data = new LifangParkingLotCfgRpcResp();
                data.setId(item.getId());
                data.setSecret(((LifangParkingLotConfiguration) item).getSecret());
                data.setBaseUrl(((LifangParkingLotConfiguration) item).getBaseUrl());

                data.setManagerType(ParkingLotManagerEnum.fromMessage(item.getManagerType()).getCode());
                data.setDescription(item.getDescription());
                result.add(data);
            }
        });
        return result;
    }

    /**
     * 健康检查
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public Map<Integer, Map<Long, Boolean>> parkingManagerHealthCheck(@Nullable Integer parkingLotManagerCode, @Nullable String parkingLotId) {
        ParkingLotManagerEnum parkingLotManagerEnum = ParkingLotManagerEnum.fromCode(parkingLotManagerCode);
        Map<String, Map<Long, Boolean>> health;
        if (ObjectUtil.isNotNull(parkingLotManagerEnum)) {
            health = factory.healthCheck(parkingLotManagerEnum.getName(), parkingLotId);
        } else {
            health = factory.healthCheck(null, parkingLotId);
        }

        if (health.isEmpty() || health.values().isEmpty()) {
            throw new NoSuchElementException(parkingLotManagerCode + ":" + parkingLotId + "不存在");
        }


        Map<Integer, Map<Long, Boolean>> result = new HashMap<>();
        health.forEach((key, value) -> {
            result.put(ParkingLotManagerEnum.valueOf(key).getCode(), value);
        });
        return result;
    }
}
