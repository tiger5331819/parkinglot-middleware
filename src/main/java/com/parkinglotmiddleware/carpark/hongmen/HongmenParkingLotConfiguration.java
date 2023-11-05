package com.parkinglotmiddleware.carpark.hongmen;

import com.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import lombok.Getter;
import lombok.ToString;

/**
 * 道尔停车场配置信息
 *
 * @author Suhuyuan
 */
@Getter
@ToString
public class HongmenParkingLotConfiguration extends ParkingLotConfiguration {

    private final String appId;
    private final String secret;
    private final String baseUrl;

    public HongmenParkingLotConfiguration(String id, String appId, String secret, String baseUrl, String description) {
        super(id, description, "Hongmen");
        this.appId = appId;
        this.secret = secret;
        this.baseUrl = baseUrl;
    }
}
