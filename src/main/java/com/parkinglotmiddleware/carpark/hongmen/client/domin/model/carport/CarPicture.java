package com.parkinglotmiddleware.carpark.hongmen.client.domin.model.carport;


import lombok.Data;

/**
 * 临停车费用
 *
 * @author Suhuyuan
 */
@Data
public class CarPicture {

    /**
     * 停车场编号
     */
    private String parkingId;
    /**
     * 出入场类型
     * <p>
     * IN:入场
     * OUT:出场
     */
    private String inOutType;
    /**
     * 记录 ID
     * <p>
     * 出入场记录 ID，对应“VEHICLE_ENTER”或“VEHICLE_EXIT”中的 ID
     */
    private String recordId;
}
