package com.parkinglotmiddleware.carpark.jieshun;

import com.parkinglotmiddleware.carpark.jieshun.ability.JieShunAbilityService;
import com.parkinglotmiddleware.carpark.jieshun.client.JieShunClient;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunParkingLot extends ParkingLotPod {
    private final JieShunClient jieShun;

    public JieShunParkingLot(JieShunParkingLotConfiguration jieShunParkingLotInfo, RedisTool redis) {
        super(jieShunParkingLotInfo, redis);
        jieShun = new JieShunClient("", 3);
    }

    @Override
    public ParkingLotAbilityService ability() {
        return new JieShunAbilityService();
    }

    @Override
    public <T> T client() {
        JieShunParkingLotConfiguration configuration = (JieShunParkingLotConfiguration) cfg;
        return (T) new JieShunClient("", -1);
    }

    @Override
    public Boolean healthCheck() {
        try {
            return true;
            //return jieShun.getCarPortInfo().block().getHead().getStatus() == 1;
        } catch (Exception ex) {
            log.error(cfg.getId() + "健康检查异常", ex);
            return false;
        }
    }
}
