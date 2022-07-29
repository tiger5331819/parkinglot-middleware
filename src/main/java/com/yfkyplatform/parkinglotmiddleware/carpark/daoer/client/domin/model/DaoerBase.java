package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.universal.web.WebRequestBase;
import lombok.Getter;

/**
 * 道尔基础请求类
 *
 * @author Suhuyuan
 */
@Getter
public class DaoerBase extends WebRequestBase {
    /**
     * 车场ID
     */
    private String parkId;
    /**
     * 车牌号
     */
    private String carNo;

    public DaoerBase(String uri) {
        super(uri);
    }
    public DaoerBase(String uri,String carNo){
        super(uri);
        if(!StrUtil.isBlank(carNo)){
            this.carNo=carNo;
        }
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
