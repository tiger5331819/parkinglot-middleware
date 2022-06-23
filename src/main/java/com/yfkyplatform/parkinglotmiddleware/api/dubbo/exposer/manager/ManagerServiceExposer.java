package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.manager;


import cn.hutool.core.bean.BeanUtil;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.DaoerParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
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
        return managerSet.stream().map(item -> ParkingLotManagerEnum.valueOf(item).value()).collect(Collectors.toSet());
    }

    /**
     * 获取配置文件
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<ParkingLotCfgRpcResp> parkingMangerConfiguration(@Nullable Integer parkingLotManagerCode, @Nullable Long parkingLotId) {
        List<ParkingLotConfiguration> cfgList = factory.getParkingLotConfiguration(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).message(), parkingLotId);

        List<ParkingLotCfgRpcResp> result = new ArrayList<>();
        cfgList.forEach(item -> {
            if ((item instanceof DaoerParkingLotConfiguration)) {
                result.add(BeanUtil.copyProperties(item, DaoerParkingLotCfgRpcResp.class));
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
    public Map<Integer, Map<Long, Boolean>> parkingManagerHealthCheck(@Nullable Integer parkingLotManagerCode, @Nullable Long parkingLotId) {
        Map<String, Map<Long, Boolean>> health = factory.healthCheck(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).message(), parkingLotId);
        Map<Integer, Map<Long, Boolean>> result = new HashMap<>();
        health.forEach((key, value) -> {
            result.put(ParkingLotManagerEnum.valueOf(key).value(), value);
        });
        return result;
    }
}
