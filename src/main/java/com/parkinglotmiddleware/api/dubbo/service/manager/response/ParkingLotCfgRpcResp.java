package com.parkinglotmiddleware.api.dubbo.service.manager.response;

import com.yfkyframework.common.core.domain.BaseRpcReq;
import lombok.Data;
import lombok.ToString;

/**
 * 停车场基本信息
 *
 * @author Suhuyuan
 */
@Data
@ToString
public class ParkingLotCfgRpcResp extends BaseRpcReq {
    /**
     * 配置信息唯一标识
     */
    private String id;
    /**
     * 描述
     */
    private String description;
    /**
     * 所属管理器类型
     */
    private Integer managerType;
}
