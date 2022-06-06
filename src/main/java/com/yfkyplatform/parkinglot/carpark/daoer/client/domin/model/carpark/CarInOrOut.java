package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.carpark;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.IPageModel;
import lombok.Getter;
/**
 * 车辆入场记录
 *
 * @author Suhuyuan
 */
@Getter
public class CarInOrOut extends DaoerBase implements IPageModel {
    private String startTime;
    private String endTime;
    private int pageNum=1;
    private int pageSize=10;

    public CarInOrOut(String uri){
        super(uri);
    }

    @Override
    public void configPage(int pageNum, int pageSize) {
        if(ObjectUtil.isNotNull(pageNum)&&ObjectUtil.isNotNull(pageSize)){
            this.pageNum=pageNum;
            this.pageSize=pageSize;
        }
    }

    public void setStartTime(String startTime){
        if(!StrUtil.isBlank(startTime)){
            this.startTime=startTime;
        }
    }

    public void setEndTime(String endTime){
        if(!StrUtil.isBlank(endTime)){
            this.endTime=endTime;
        }
    }
}
