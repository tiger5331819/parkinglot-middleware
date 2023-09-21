package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;

/**
 * 停车场配置项
 *
 * @author Suhuyuan
 */
@Data
public class ParkingLotConfiguration<T> {

    private String id;

    private T config;

    private String managerType;

    private String description;
}
