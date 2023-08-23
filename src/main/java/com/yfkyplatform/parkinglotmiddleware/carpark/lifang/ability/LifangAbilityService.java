package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.LifangParkingLotClient;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.ParkingLotAbilityService;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.tool.IToolAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;

/**
 * 立方车场能力服务
 *
 * @author Suhuyuan
 */

public class LifangAbilityService extends ParkingLotAbilityService {

    private final LifangParkingLotClient Lifang;

    private final RedisTool redis;

    public LifangAbilityService(LifangParkingLotClient lifang, RedisTool redis) {
        Lifang = lifang;

        this.redis = redis;
    }

    /**
     * 工具
     *
     * @return
     */
    @Override
    public IToolAblitity tool() {
        return new LifangToolAbility(Lifang);
    }

    /**
     * 车场
     *
     * @return
     */
    @Override
    public ICarPortAblitity carport() {
        return new LifangCarPortAbility(Lifang, redis);
    }
}
