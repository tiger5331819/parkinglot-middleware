package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport;

import lombok.Data;

/**
 * 车位使用情况结果
 *
 * @author Suhuyuan
 */
@Data
public class CarportResult{
    /**
     * 总车位
     */
    private CarportData total;
    /**
     * 剩余空车位
     */
    private CarportData idle;

}
