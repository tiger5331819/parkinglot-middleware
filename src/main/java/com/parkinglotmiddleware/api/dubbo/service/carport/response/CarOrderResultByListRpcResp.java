package com.parkinglotmiddleware.api.dubbo.service.carport.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 临时车缴费订单结果
 *
 * @author Suhuyuan
 */
@Data
@ToString(callSuper = true)
public class CarOrderResultByListRpcResp extends com.parkinglotmiddleware.api.dubbo.service.carport.response.CarOrderResultRpcResp {

    /**
     * 欠费订单
     */
    private List<CarOrderResultRpcResp> arrearList;
}
