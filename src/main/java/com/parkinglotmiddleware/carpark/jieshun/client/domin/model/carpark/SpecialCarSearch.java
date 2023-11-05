package com.parkinglotmiddleware.carpark.jieshun.client.domin.model.carpark;

import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.model.IPageModel;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 查询特殊车辆
 *
 * @author Suhuyuan
 */
@Data
public class SpecialCarSearch extends JieShunBase implements IPageModel {
    private int pageNum = 1;
    private int pageSize = 10;

    @Override
    public void configPage(int pageNum, int pageSize) {
        if (ObjectUtil.isNotNull(pageNum) && ObjectUtil.isNotNull(pageSize)) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
        }
    }
}
