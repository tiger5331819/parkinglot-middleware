package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 通道信息
 *
 * @author Suhuyuan
 */
@Data
public class DueCarSuccess extends DaoerBase {

    /**
     * 通道ID
     */
    private String dsn;

}
