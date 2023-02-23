package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.IPageModel;
import lombok.Data;

/**
 * 查询特殊车辆
 *
 * @author Suhuyuan
 */
@Data
public class SpecialCarSearch extends DaoerBase implements IPageModel {
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
