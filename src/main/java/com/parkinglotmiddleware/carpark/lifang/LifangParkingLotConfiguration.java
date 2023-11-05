package com.parkinglotmiddleware.carpark.lifang;

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
public class LifangParkingLotConfiguration extends ParkingLotConfiguration {
    private final String secret;
    private final String baseUrl;

    public LifangParkingLotConfiguration(String id, String secret, String baseUrl, String description) {
        super(id, description, "Lifang");
        this.secret = secret;
        this.baseUrl = baseUrl;
    }
}
