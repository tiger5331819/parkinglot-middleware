package com.parkinglotmiddleware.api.dubbo.service.manager.response;

import lombok.Data;
import lombok.ToString;

/**
 * 道尔停车场配置信息
 *
 * @author Suhuyuan
 */
@Data
@ToString
public class DaoerParkingLotCfgRpcResp extends ParkingLotCfgRpcResp {
    private String appName;
    private String parkId;
    private String baseUrl;
}
