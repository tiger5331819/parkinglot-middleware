package com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport;

import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import lombok.Data;

import java.util.List;

/**
 * 车位使用情况结果
 *
 * @author Suhuyuan
 */
@Data
public class CarportResult extends LifangBaseResp {
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
    private String parkID;
    /**
     * 车场名称
     */
    private String parkName;
    /**
     * 车场收费标准
     */
    private String chargeRuleDesc;
    /**
     * 区域余位信息
     */
    private List<CarportInfo> parkingLotInfo;
}
