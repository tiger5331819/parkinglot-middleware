package com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport;

import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import lombok.Data;

/**
 * 车位使用情况结果
 *
 * @author Suhuyuan
 */
@Data
public class CarportInfo extends LifangBaseResp {
    /**
     * 总车位
     */
    private int totalNum;
    /**
     * 总已停车位
     */
    private int totalStopNum;
    /**
     * 总剩余车位
     */
    private int totalRemainNum;
    /**
     * 车场ID
     */
    private int parkingLotId;
    /**
     * 车场名称
     */
    private String parkingLotName;

}
