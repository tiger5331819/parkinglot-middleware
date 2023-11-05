package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 月租车基本信息
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarResult {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 卡类型编号：11，月租卡A；12，月租卡B；13，月租卡C；14，月租卡D
     */
    private int cardTypeId;
    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateTime;
}
