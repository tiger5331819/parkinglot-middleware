package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;

/**
 * 停车场配置项
 *
 * @author Suhuyuan
 */
@Data
public class ParkingLotConfiguration<T> {

    private String parkingLotId;

    private T config;

    private String parkingType;

    private String description;
}
