package com.yfkyplatform.parkinglotmiddleware.domain.manager.container;

import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.ParkingLotAbility;

/**
 * 停车场容器
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotPod implements ParkingLotAbility {

    protected ParkingLotConfiguration cfg;

    protected RedisTool redis;

    public ParkingLotPod(ParkingLotConfiguration parkingLotConfiguration, RedisTool redis) {
        this.cfg = parkingLotConfiguration;
        this.redis = redis;
    }

    /**
     * 获取驱动
     *
     * @param <T>
     * @return
     */
    public abstract <T> T client();

    /**
     * 获取配置信息
     * @return
     * @param <T>
     */
    public <T extends ParkingLotConfiguration> T configuration(){
        return (T) cfg;
    }

    /**
     * 健康检查
     * @return
     */
    public abstract boolean healthCheck();

    /**
     * 获取停车场Id
     *
     * @param
     * @return
     */
    public Long Id() {
        return cfg.getId();
    }
}
