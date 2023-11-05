package com.parkinglotmiddleware.api.dubbo.service.manager;

import com.parkinglotmiddleware.api.dubbo.service.manager.response.ParkingLotCfgRpcResp;
import com.yfkyframework.common.core.exception.ExposerException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Suhuyuan
 */
public interface IManagerService {
    /**
     * 获取支持的管理器列表
     *
     * @return
     */
    Set<Integer> managerSupport() throws ExposerException;

    /**
     * 获取配置文件
     *
     * @param parkingLotManagerName 停车场管理名称 (可空)
     * @param parkingLotId          停车场Id （可空）
     * @return
     */
    List<ParkingLotCfgRpcResp> parkingMangerConfiguration(@Nullable Integer parkingLotManagerName, @Nullable String parkingLotId) throws ExposerException;

    /**
     * 健康检查
     *
     * @param parkingLotManagerName 停车场管理名称（可空）
     * @param parkingLotId          停车场Id（可空）
     * @return
     */
    Map<Integer, Map<Long, Boolean>> parkingManagerHealthCheck(@Nullable Integer parkingLotManagerName, @Nullable String parkingLotId) throws ExposerException;
}
