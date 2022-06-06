package com.yfkyplatform.parkinglot.domain.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class DaoerParkingLotConfiguration implements Serializable {
    private String appName;
    private String parkId;
    private String baseUrl;

    public DaoerParkingLotConfiguration(String appName, String parkId, String baseUrl) {
        this.appName = appName;
        this.parkId = parkId;
        this.baseUrl = baseUrl;
    }
}