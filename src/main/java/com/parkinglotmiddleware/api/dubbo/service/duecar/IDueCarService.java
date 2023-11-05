package com.parkinglotmiddleware.api.dubbo.service.duecar;

import com.parkinglotmiddleware.api.dubbo.service.duecar.request.DueCarConfigurationRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.duecar.request.DueCarSuccessRpcReq;
import com.yfkyframework.common.core.exception.ExposerException;

/**
 * 停车场催缴服务
 *
 * @author Suhuyuan
 */
public interface IDueCarService {
     /**
      * 补缴成功通知
      *
      * @param dueCarSuccessRpcReq 补缴成功信息
      * @return
      */
     void dueCarAccess(DueCarSuccessRpcReq dueCarSuccessRpcReq) throws ExposerException;

     /**
      * 同步补缴配置信息
      *
      * @param dueCarConfigurationRpcReq 同步补缴配置信息请求
      * @return
      */
     void configDueCar(DueCarConfigurationRpcReq dueCarConfigurationRpcReq) throws ExposerException;

}
