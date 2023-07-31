package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import lombok.Getter;
import lombok.ToString;

/**
 * 捷顺停车场配置信息
 *
 * @author Suhuyuan
 */
@Getter
@ToString
public class JieShunParkingLotConfiguration extends ParkingLotConfiguration {
    private final String secret;
    private final String baseUrl;

    public JieShunParkingLotConfiguration(String id, String secret, String baseUrl, String description) {
        super(id, description, "JieShun");
        this.secret = secret;
        this.baseUrl = baseUrl;
    }
}
