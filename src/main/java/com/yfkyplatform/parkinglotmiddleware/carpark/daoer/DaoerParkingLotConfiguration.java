package com.yfkyplatform.parkinglotmiddleware.carpark.daoer;

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
public class DaoerParkingLotConfiguration extends ParkingLotConfiguration {
    private String appName;
    private String parkId;
    private String baseUrl;

    public DaoerParkingLotConfiguration(String id, String appName, String parkId, String baseUrl) {
        super(id);
        this.appName = appName;
        this.parkId = parkId;
        this.baseUrl = baseUrl;
    }
}
