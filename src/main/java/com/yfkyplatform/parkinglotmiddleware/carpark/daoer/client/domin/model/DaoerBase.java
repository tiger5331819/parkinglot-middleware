package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 道尔基础请求类
 *
 * @author Suhuyuan
 */
@Data
public class DaoerBase {
    /**
     * 车场ID
     */
    private String parkId;
    /**
     * 车牌号
     */
    private String carNo;

    public DaoerBase() {
    }

    public void setCarNo(String carNo){
        if(!StrUtil.isBlank(carNo)){
            this.carNo=carNo;
        }
    }

    public void setParkId(String parkId){
        if (!StrUtil.isBlank(parkId) && StrUtil.isBlank(this.parkId)) {
            this.parkId = parkId;
        }
    }
}
