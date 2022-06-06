package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.api;

import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.guest.GuestResult;
import reactor.core.publisher.Mono;

/**
 *访客接口
 *
 * @author Suhuyuan
 */
public interface IDaoerGuest {
    /**
     * 创建访客
     * @return
     */
    Mono<DaoerBaseResp<GuestResult>> createGuest(String guestName, String carNo, String visitTime, String mobile, String description);

    /**
     * 修改访客
     * @return
     */
    Mono<DaoerBaseResp> changeGuestMessage(String objectId,String guestName,String visitTime,String mobile,String description);

    /**
     * 删除访客
     * @return
     */
    Mono<DaoerBaseResp> removeGuestMessage(String objectId);
}
