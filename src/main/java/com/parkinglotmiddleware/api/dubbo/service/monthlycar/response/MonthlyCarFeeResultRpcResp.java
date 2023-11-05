package com.parkinglotmiddleware.api.dubbo.service.monthlycar.response;

import com.yfkyframework.common.core.domain.BaseRpcResp;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 月租车续费信息
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarFeeResultRpcResp extends BaseRpcResp {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 卡类型编号
     */
    private int cardTypeId;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime startTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime endTime;
    /**
     * 车主姓名
     */
    private String contactName;
    /**
     * 车主电话号码
     */
    private String contactPhone;
    /**
     * 0正常,2即将过期，6过期, 7待审核，8待支付 -1注销,
     */
    private int status;
    /**
     * 最后操作时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime lastUpdateTime;
    /**
     * 月卡费率列表
     */
    private List<MonthlyCarRateMessage> RateMessageList;
}
