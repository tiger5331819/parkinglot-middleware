package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;


import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Data
public class SaveSepcialCar extends DaoerBase {
    /**
     * 1黑名单/2特殊车辆
     */
    private int carNoType;
    /**
     * 通行类型 类型(1禁止通行,2通行免费,3自由通行)
     */
    private int isStop;

    /**
     * 备注
     */
    private String description;

    /**
     * 唯一表示，修改必传
     */
    private String objectId;
}
