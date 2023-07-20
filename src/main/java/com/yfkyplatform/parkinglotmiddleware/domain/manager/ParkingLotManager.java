package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.IParkingLotConfigurationRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.*;

/**
 * 停车场管理
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class ParkingLotManager<T extends ParkingLotPod, Data extends ParkingLotConfiguration> {

    private final ParkingLotManagerInfrastructure infrastructure;

    public ParkingLotManager(ParkingLotManagerInfrastructure infrastructure, String managerType) {
        this.redis = infrastructure.getRedis();
        this.infrastructure = infrastructure;
        this.managerType = managerType;
    }

    protected RedisTool redis;

    protected String managerType;

    protected IParkingLotConfigurationRepository cfgRepository() {
        return infrastructure.getCfgRepository();
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
     *
     * @param data
     * @return
     */
    protected abstract Boolean SaveData(Data data);

    /**
     * 添加停车场
     *
     * @param data 停车场配置数据
     * @return
     */
    public Boolean addParkingLot(Data data) {
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
        if (ObjectUtil.isNull(parkingLotId)) {
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
     *
     * @param parkingLotId
     * @return
     */
    public List<ParkingLotConfiguration> configurationList(@Nullable String parkingLotId) {
        List cfgList = new ArrayList();

        if (ObjectUtil.isNotNull(parkingLotId)) {
            try {
                cfgList.add(parkingLot(parkingLotId).configuration());
            } catch (Exception ex) {
            }
        } else {
            load().forEach(item -> cfgList.add(item.configuration()));
        }
        return cfgList;
    }

    /**
     * 获取停车场健康检查结果
     *
     * @param parkingLotId
     * @return
     */
    public Map<String, Boolean> parkingLotHealthCheck(@Nullable String parkingLotId) {
        Map<String, Boolean> healthCheckMap = new HashMap(100);
        if (ObjectUtil.isNotNull(parkingLotId)) {
            try {
                T parkingLot = parkingLot(parkingLotId);
                healthCheckMap.put(parkingLot.Id(), parkingLot.healthCheck());
            } catch (Exception ex) {
            }
        } else {
            load().forEach(item -> healthCheckMap.put(item.Id(), item.healthCheck()));
        }
        return healthCheckMap;
    }
}
