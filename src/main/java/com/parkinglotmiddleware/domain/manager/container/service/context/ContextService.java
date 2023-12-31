package com.parkinglotmiddleware.domain.manager.container.service.context;

import cn.hutool.json.JSONUtil;
import com.parkinglotmiddleware.universal.AssertTool;
import com.parkinglotmiddleware.universal.RedisTool;

import java.time.Duration;
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
        if (AssertTool.checkCarNotNull(car)) {
            return check(car.getCarNo());
        } else {
            throw new IllegalArgumentException("车辆实体不存在");
        }

    }

    public Car createCar(String carNo) {
        Car car = new Car();
        car.setCarNo(carNo);
        car.setCarSpace(new Space());
        add(car);
        return car;
    }

    private void add(Car car) {
        if (check(car)) {
            throw new RuntimeException("车辆已存在");
        } else {
            redis.set(makeKey(car.getCarNo()), JSONUtil.toJsonStr(car), Duration.ofMinutes(5));
        }
    }

    public Car get(String carNo) {
        return JSONUtil.toBean((String) redis.get(makeKey(carNo)), Car.class);
    }


    public void remove(String carNo) {
        redis.delete(makeKey(carNo));
    }

    public void update(Car car) {
        if (check(car)) {
            redis.set(makeKey(car.getCarNo()), JSONUtil.toJsonStr(car), Duration.ofMinutes(5));
        } else {
            throw new NoSuchElementException("车辆不存在");
        }
    }

}
