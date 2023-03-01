package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.guest;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访客基本信息
 *
 * @author Suhuyuan
 */
@Data
public class GuestMessage extends DaoerBase {
    /**
     * 访客名称
     */
    private String guestName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 预计入场时间【需在预计入场时间前入场】
     */
    private LocalDateTime visitTime;
    /**
     * 访问理由
     */
    private String description;

    /**
     * 车场编号
     */
    public String parkingNo(){return getParkId();}

    public void setGuestName(String guestName) {
        if(!StrUtil.isBlank(guestName)){
            this.guestName=guestName;
        }
    }

    public void setMobile(String mobile) {
        if(!StrUtil.isBlank(mobile)){
            this.mobile=mobile;
        }
    }

    public void setVisitTime(LocalDateTime visitTime) {
        if(ObjectUtil.isNotNull(visitTime)){
            this.visitTime=visitTime;
        }
    }

    public void setDescription(String description) {
        if(!StrUtil.isBlank(description)){
            this.description=description;
        }
    }
}
