package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 道尔基础请求类
 *
 * @author Suhuyuan
 */
@Getter
@NoArgsConstructor
public class DaoerBase {
    /**
     * 车场ID
     */
    private String parkId;
    /**
     * API URI
     */
    private String uri;
    /**
     * 车牌号
     */
    private String carNo;

    public DaoerBase(String uri){
        this.uri= uri;
    }
    public DaoerBase(String uri,String carNo){
        if(!StrUtil.isBlank(carNo)){
            this.carNo=carNo;
        }
        this.uri= uri;
    }

    public void setCarNo(String carNo){
        if(!StrUtil.isBlank(carNo)){
            this.carNo=carNo;
        }
    }

    public void setParkId(String parkId){
        if(!StrUtil.isBlank(parkId)){
            this.parkId=parkId;
        }
    }

    public void appendUri(String appendUri){
        uri+=appendUri;
    }
}
