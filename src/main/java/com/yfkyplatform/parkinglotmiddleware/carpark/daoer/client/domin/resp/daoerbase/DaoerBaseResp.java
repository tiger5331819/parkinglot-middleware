package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase;

import lombok.Data;

/**
 * 道尔云基础返回结果
 *
 * @author Suhuyuan
 */
@Data
public class DaoerBaseResp<T> {
    private DaoerBaseRespHead head;
    private T body;
}
