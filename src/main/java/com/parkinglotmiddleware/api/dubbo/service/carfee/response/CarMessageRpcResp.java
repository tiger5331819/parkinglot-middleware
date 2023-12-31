package com.parkinglotmiddleware.api.dubbo.service.carfee.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆信息
 *
 * @author Suhuyuan
 */
@Data
public class CarMessageRpcResp extends BaseRpcResp {
    private String carNo;

    /**
     * 车辆月租类型
     */
    private int typeId = -1;

    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeStartTime;

    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeEndTime;

    /**
     * 0正常,2即将过期，6过期, 7待审核，8待支付 -1注销,
     */
    private int typeStatus;

    /**
     * 车主姓名
     */
    private String contactName = "临时用户";

    /**
     * 车主电话号码
     */
    private String contactPhone;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime inTime;

    /**
     * 入场照片
     */
    private String inPic;

    /**
     * 车场唯一入场记录ID
     */
    private String inId;

}
