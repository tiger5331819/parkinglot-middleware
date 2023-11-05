package com.parkinglotmiddleware.api.dubbo.service;

import com.yfkyframework.common.core.domain.BaseRpcReq;
import lombok.Data;

/**
 * 停车场信息
 *
 * @author Suhuyuan
 */
@Data
public class ParkingLotRpcReq extends BaseRpcReq {

    /**
     * 停车场管理MFR
     */
    Integer parkingLotManagerCode;

    /**
     * 停车场Id
     */
    String parkingLotId;

    /**
     * 运营商Id
     */
    Integer operatorId;
}
