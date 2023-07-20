package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import lombok.Getter;
import lombok.ToString;

/**
 * 道尔停车场配置信息
 *
 * @author Suhuyuan
 */
@Getter
@ToString
public class JieShunParkingLotConfiguration extends ParkingLotConfiguration {
    private final String appName;
    private final String parkId;
    private final String baseUrl;

    private final String imgUrl;

    private final Boolean backTrack;

    public JieShunParkingLotConfiguration(String id, String appName, String parkId, String baseUrl, String description, String imgUrl, Boolean backTrack) {
        super(id, description, "Daoer");
        this.appName = appName;
        this.parkId = parkId;
        this.baseUrl = baseUrl;
        this.imgUrl = imgUrl;
        this.backTrack = Boolean.TRUE.equals(backTrack);
    }
}
