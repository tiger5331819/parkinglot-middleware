package com.yfkyplatform.parkinglotmiddleware.api.dubbo.manager;


import cn.hutool.core.bean.BeanUtil;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.DaoerParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class ManagerService implements IManagerService {

    private final ParkingLotManagerFactory factory;

    public ManagerService(ParkingLotManagerFactory factory){
        this.factory=factory;
    }


    /**
     * 获取支持的管理器列表
     *
     * @return
     */
    @Override
    public Set<String> managerSupport() {
        return factory.getManagerSupport();
    }

    /**
     * 获取配置文件
     *
     * @param parkingLotManagerName 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<ParkingLotCfgRpcResp> parkingMangerConfiguration(String parkingLotManagerName, String parkingLotId) {
        List<ParkingLotConfiguration> cfgList=factory.getParkingLotConfiguration(parkingLotManagerName, parkingLotId);

        List<ParkingLotCfgRpcResp> result=new ArrayList<>();
        cfgList.forEach(item->{
            if((item instanceof DaoerParkingLotConfiguration)){
                result.add(BeanUtil.copyProperties(item, DaoerParkingLotCfgRpcResp.class));
            }
        });
        return result;
    }

    /**
     * 健康检查
     *
     * @param parkingLotManagerName 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public Map<String, Map<String, Boolean>> parkingManagerHealthCheck(String parkingLotManagerName, String parkingLotId) {
        return factory.healthCheck(parkingLotManagerName, parkingLotId);
    }
}
