package com.yfkyplatform.parkinglotmiddleware.universal.duecar;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.yfkyplatform.presspay.api.PresspayLinkedServiceApi;
import com.yfkyplatform.presspay.api.req.QueryUrgePayConfReq;
import com.yfkyplatform.presspay.api.req.QueryUrgePayMsgRpcReq;
import com.yfkyplatform.presspay.api.req.SynUrgePayConfReq;
import com.yfkyplatform.presspay.api.resp.PresspayUrgePayConfResp;
import com.yfkyplatform.presspay.api.resp.QueryUrgePayMsgRpcResp;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

/**
 * SaaS 联合催缴服务代理
 *
 * @author Suhuyuan
 */
@Component
public class DueCarProxy {
    @DubboReference
    private PresspayLinkedServiceApi presspayLinkedServiceApi;

    /**
     * 查询是否是催缴车辆
     *
     * @return
     */
    public QueryUrgePayMsgRpcResp checkDueCar(Integer operatorId, ParkingLotConfiguration parkingLotCfg, DueCar dueCar) {
        QueryUrgePayMsgRpcReq queryUrgePayMsgRpcReq = new QueryUrgePayMsgRpcReq();
        queryUrgePayMsgRpcReq.setMfrCode(ParkingLotManagerEnum.fromMessage(parkingLotCfg.getManagerType()).getCode());
        queryUrgePayMsgRpcReq.setParkinglotId(Long.valueOf(parkingLotCfg.getId()));
        queryUrgePayMsgRpcReq.setPlateNumber(dueCar.getPlateNumber());
        queryUrgePayMsgRpcReq.setPlateColor(dueCar.getPlateColor());
        queryUrgePayMsgRpcReq.setVehicleType(dueCar.getVehicleType());
        queryUrgePayMsgRpcReq.setOperatorId(operatorId);

        return presspayLinkedServiceApi.queryUrgePayByPlateNumber(queryUrgePayMsgRpcReq);
    }

    /**
     * 联动催缴配置同步
     *
     * @return
     */
    public void syncDueCarConfiguration(Integer operatorId, ParkingLotConfiguration parkingLotCfg, DueConfiguration dueConfiguration) {
        SynUrgePayConfReq synUrgePayConfReq = new SynUrgePayConfReq();
        synUrgePayConfReq.setMfrCode(ParkingLotManagerEnum.fromMessage(parkingLotCfg.getManagerType()).getCode());
        synUrgePayConfReq.setOperatorId(operatorId);
        synUrgePayConfReq.setParkinglotId(Long.valueOf(parkingLotCfg.getId()));
        synUrgePayConfReq.setUrgepayNotIn(dueConfiguration.getUrgepayNotIn());
        synUrgePayConfReq.setUrgepayNotOut(dueConfiguration.getUrgepayNotOut());
        synUrgePayConfReq.setStartTime(dueConfiguration.getStartTime());
        synUrgePayConfReq.setCloseTime(dueConfiguration.getCloseTime());

        presspayLinkedServiceApi.synUrgePayConf(synUrgePayConfReq);
    }

    /**
     * 查询联动催缴配置
     *
     * @return
     */
    public PresspayUrgePayConfResp findDueCarConfiguration(Integer operatorId, ParkingLotConfiguration parkingLotCfg) {
        QueryUrgePayConfReq queryUrgePayConfReq = new QueryUrgePayConfReq();
        queryUrgePayConfReq.setMfrCode(ParkingLotManagerEnum.fromMessage(parkingLotCfg.getManagerType()).getCode());
        queryUrgePayConfReq.setOperatorId(operatorId);
        queryUrgePayConfReq.setParkinglotId(Long.valueOf(parkingLotCfg.getId()));

        PresspayUrgePayConfResp result = presspayLinkedServiceApi.queryUrgePayConf(queryUrgePayConfReq);

        if (ObjectUtil.isNull(result)) {
            result = new PresspayUrgePayConfResp();
        }

        if (ObjectUtil.isNull(result.getUrgepayNotIn())) {
            result.setUrgepayNotIn(1);
        }

        if (ObjectUtil.isNull(result.getUrgepayNotOut())) {
            result.setUrgepayNotOut(1);
        }

        return result;
    }
}
