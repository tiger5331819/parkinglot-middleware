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

    private Long parkingLotId;

    private T config;

    private String parkingType;

    private String description;

    public ParkingLotConfiguration(Long parkingLotId, String parkingType, String description) {
        this.parkingLotId = parkingLotId;
        this.parkingType = parkingType;
        this.description = description;
    }
}
