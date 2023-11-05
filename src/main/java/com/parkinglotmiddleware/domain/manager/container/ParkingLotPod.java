package com.parkinglotmiddleware.domain.manager.container;

import com.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.parkinglotmiddleware.domain.manager.container.service.carport.CarPortService;
import com.parkinglotmiddleware.domain.manager.container.service.carport.DueCarService;
import com.parkinglotmiddleware.domain.manager.container.service.context.ContextService;
import com.parkinglotmiddleware.universal.RedisTool;

/**
 * 停车场容器
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotPod {

    protected final ParkingLotConfiguration cfg;

    protected final RedisTool redis;

    protected final ContextService contextService;

    private final CarPortService carPortService;

    private final DueCarService dueCarService;

    public ParkingLotPod(ParkingLotConfiguration parkingLotConfiguration, RedisTool redis) {
        this.cfg = parkingLotConfiguration;
        this.redis = redis;
        contextService=new ContextService(cfg.getDescription(), redis);
        carPortService=new CarPortService(this, contextService);
        dueCarService=new DueCarService(this,carPortService,redis);
    }

    public CarPortService carPort() {
        return carPortService;
    }

    public DueCarService dueCar() {
        return dueCarService;
    }

    public abstract ParkingLotAbilityService ability();

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
     *
     * @return
     */
    public abstract Boolean healthCheck();

    /**
     * 获取停车场Id
     *
     * @param
     * @return
     */
    public String id() {
        return cfg.getId();
    }
}
