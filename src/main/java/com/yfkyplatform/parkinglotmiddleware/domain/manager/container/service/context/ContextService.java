package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context;

import cn.hutool.json.JSONUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.monthly.MonthlyCarMessageResult;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;

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
        if (AssertTool.checkEntityNotNull(car)) {
            return check(car.getCarNo());
        } else {
            throw new IllegalArgumentException("车辆实体不存在");
        }

    }

    public Car createCar(String carNo) {
        Car car = new Car();
        car.setCarNo(carNo);
        add(car);
        return car;
    }

    public Car addMonthlyMessage(String carNo, MonthlyCarMessageResult monthlyCarMessage) {
        Car car = get(carNo);

        if (AssertTool.checkEntityNotNull(car)) {
            car = createCar(carNo);
        }

        if (AssertTool.checkEntityNotNull(monthlyCarMessage)) {
            car.setTypeId(monthlyCarMessage.getCardTypeId());
            car.setTypeStartTime(monthlyCarMessage.getStartTime());
            car.setTypeEndTime(monthlyCarMessage.getEndTime());
            car.setTypeStatus(monthlyCarMessage.getStatus());
            car.setContactName(monthlyCarMessage.getContactName());
            car.setContactPhone(monthlyCarMessage.getContactPhone());
        }

        update(car);
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
        redis.getWithDelete(makeKey(carNo));
    }

    public void update(Car car) {
        if (check(car)) {
            redis.set(makeKey(car.getCarNo()), JSONUtil.toJsonStr(car), Duration.ofMinutes(5));
        } else {
            throw new NoSuchElementException("车辆不存在");
        }
    }

}
