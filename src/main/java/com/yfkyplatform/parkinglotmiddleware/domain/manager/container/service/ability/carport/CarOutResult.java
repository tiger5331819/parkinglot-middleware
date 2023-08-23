package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 车辆出场数据
 *
 * @author Suhuyuan
 */
@Data
public class CarOutResult {
    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 车类型ID
     */
    private int cardTypeId;
    /**
     * 车类型名称，如临时车A
     */
    private String cardTypeName;

    /**
     * 入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;
    /**
     * 入场图片
     */
    private String inPic;
    /**
     * 出场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;
    /**
     * 出场图片
     */
    private String outPic;
}
