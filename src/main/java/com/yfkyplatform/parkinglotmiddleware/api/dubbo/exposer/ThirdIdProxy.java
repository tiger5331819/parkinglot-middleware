package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer;

import com.yfkyplatform.passthrough.api.mgnt.PtParkingLotServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.stereotype.Component;

/**
 * 获取SAAS第三方ID代理
 *
 * @author Suhuyuan
 */
@Slf4j
@Component
public class ThirdIdProxy {
    @DubboReference
    private PtParkingLotServiceApi passThroughParkingLotService;

    /**
     * 通过通行服务获取第三方车场Id
     *
     * @return
     */
    public String getThirdId(Long parkingLotId, Integer operatorId) {
        try {
            return passThroughParkingLotService.getByParkingLotIdAndOperatorId(parkingLotId, operatorId).getThirdId();
        } catch (RpcException ex) {
            log.error("通行服务异常", ex);
            return "X52361700001";
        }
    }
}
