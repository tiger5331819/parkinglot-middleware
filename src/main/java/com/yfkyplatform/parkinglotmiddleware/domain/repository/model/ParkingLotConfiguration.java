package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 停车场配置项
 *
 * @author Suhuyuan
 */
@Data
@NoArgsConstructor
public class ParkingLotConfiguration<T> {

    private String parkingLotId;

    private T config;

    private String parkingType;

    private String description;

    public ParkingLotConfiguration(String parkingLotId, String parkingType, String description) {
        this.parkingLotId = parkingLotId;
        this.parkingType = parkingType;
        this.description = description;
    }
}
