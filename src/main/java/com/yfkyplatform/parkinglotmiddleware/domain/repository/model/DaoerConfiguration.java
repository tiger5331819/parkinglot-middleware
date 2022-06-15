package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @author Suhuyuan
 */
@Data
@NoArgsConstructor
public class DaoerConfiguration implements Serializable {
    private String appName;
    private String parkId;
    private String baseUrl;

    public DaoerConfiguration(String appName, String parkId, String baseUrl) {
        this.appName = appName;
        this.parkId = parkId;
        this.baseUrl = baseUrl;
    }
}