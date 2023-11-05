package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 无牌车入场结果
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarInResult {
    /**
     * 通道类型
     */
    private String channelType;
    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;
    /**
     * 通过openid生成的临时车牌
     */
    private String carNo;
}
