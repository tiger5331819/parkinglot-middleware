package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.model.carport;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 在场车辆查询
 *
 * @author Suhuyuan
 */
@Data
public class SearchCar {

    /**
     * 进场开始时间  yyyyMMddHHmmss
     */
    private String fromEnterTime;

    /**
     * 进场结束时间  yyyyMMddHHmmss
     */
    private String toEnterTime;

    /**
     * 车辆号牌
     */
    private String carPlateNo;

    public void setFromEnterTime(LocalDateTime fromEnterTime) {
        if (ObjectUtil.isNotNull(fromEnterTime)) {
            this.fromEnterTime = fromEnterTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
    }

    public void setToEnterTime(LocalDateTime toEnterTime) {
        if (ObjectUtil.isNotNull(toEnterTime)) {
            this.toEnterTime = toEnterTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
    }
}
