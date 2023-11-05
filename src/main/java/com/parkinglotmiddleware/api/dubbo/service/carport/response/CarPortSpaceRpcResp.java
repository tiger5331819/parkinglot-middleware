package com.parkinglotmiddleware.api.dubbo.service.carport.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

/**
 * 车场余位
 *
 * @author Suhuyuan
 */
@Data
public class CarPortSpaceRpcResp extends BaseRpcResp {

    /**
     * 总车位数
     */
    private int total;

    /**
     * 空余车位数
     */
    private int rest;

    /**
     * 已停车位数
     */
    private int use;

}
