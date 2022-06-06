package com.yfkyplatform.parkinglot.daoerparkinglot;

import com.yfkyplatform.parkinglot.domain.manager.ParkingLotInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 道尔停车场基本信息
 *
 * @author Suhuyuan
 */
@Getter
public class DaoerParkingLotInfo extends ParkingLotInfo {
    private String appName;
    private String parkId;
    private String baseUrl;

    public DaoerParkingLotInfo(String id, String appName, String parkId, String baseUrl) {
        super(id);
        this.appName = appName;
        this.parkId = parkId;
        this.baseUrl = baseUrl;
    }
}
