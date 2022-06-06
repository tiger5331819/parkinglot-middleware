package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.daoerbase;

import lombok.Data;

/**
 * 道尔云基础返回结果—head
 *
 * @author Suhuyuan
 */
@Data
public class DaoerBaseRespHead {
    /**
     * 返回状态，1：成功；0：失败
     */
    private int status;
    /**
     * 	返回成功/失败消息
     */
    private String message;
}
