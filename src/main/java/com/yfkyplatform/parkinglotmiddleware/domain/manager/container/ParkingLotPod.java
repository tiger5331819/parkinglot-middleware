package com.yfkyplatform.parkinglotmiddleware.domain.manager.container;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.ParkingLotAbility;

/**
 * 停车场容器
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotPod implements ParkingLotAbility {

    protected ParkingLotConfiguration cfg;

    public ParkingLotPod(ParkingLotConfiguration parkingLotConfiguration){
        this.cfg= parkingLotConfiguration;
    }

    /**
     * 获取驱动
     * @return
     * @param <T>
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
     * @return
     * @param
     */
    public String Id(){
        return cfg.getId();
    }
}
