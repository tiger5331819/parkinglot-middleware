package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context;

import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;

import java.util.NoSuchElementException;

/**
 * 车位服务
 *
 * @author Suhuyuan
 */

public class ContextService {
    private final RedisTool redis;
    private final String name;

    private final String contextPrefix = "context:";

    public ContextService(String name, RedisTool redis) {
        this.redis = redis;
        this.name = name;
    }

    private String makeKey(String carNo) {
        return contextPrefix + name + ":" + carNo;
    }

    public Boolean check(String carNo) {
        String key = makeKey(carNo);
        return redis.check(key);
    }

    public Boolean check(Car car) {
        if (AssertTool.checkEntityNotNull(car)) {
            return check(car.getCarNo());
        } else {
            throw new IllegalArgumentException("车辆实体不存在");
        }

    }

    public void add(Car car) {
        if (check(car)) {
            throw new RuntimeException("车辆已存在");
        } else {
            redis.set(makeKey(car.getCarNo()), car);
        }
    }

    public Car get(String carNo) {
        return redis.get(makeKey(carNo));
    }


    public void remove(String carNo) {
        redis.getWithDelete(makeKey(carNo));
    }

    public void update(Car car) {
        if (check(car)) {
            redis.set(makeKey(car.getCarNo()), car);
        } else {
            throw new NoSuchElementException("车辆不存在");
        }
    }
}
