package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerGuest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.guest.GuestResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.GuestMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.GuestUserResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.guest.IGuestAblitity;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */

public class DaoerGuestAbility implements IGuestAblitity {

    private IDaoerGuest api;

    public DaoerGuestAbility(IDaoerGuest daoerClient){
        api=daoerClient;
    }

    /**
     * 创建访客
     *
     * @param guestMessage
     * @return
     */
    @Override
    public GuestUserResult createGuest(GuestMessage guestMessage) {
        GuestResult guestResult=api.createGuest(guestMessage.getGuestName(),guestMessage.getCarNo(),guestMessage.getVisitTime(),guestMessage.getPhone(),guestMessage.getDescription())
                .block().getBody();
        GuestUserResult result=new GuestUserResult();
        result.setGuestUserId(guestResult.getObjectId());
        result.setCarNo(guestResult.getCarNo());

        return result;
    }

    /**
     * 修改访客
     *
     * @param guestUserId
     * @param guestMessage
     * @return
     */
    @Override
    public boolean changeGuestMessage(String guestUserId, GuestMessage guestMessage) {
        return api.changeGuestMessage(guestUserId,guestMessage.getGuestName(),guestMessage.getVisitTime(),guestMessage.getPhone(),guestMessage.getDescription())
                .block().getHead().getStatus()==1;
    }

    /**
     * 删除访客
     *
     * @param guestUserId
     * @return
     */
    @Override
    public boolean removeGuestMessage(String guestUserId) {
        return api.removeGuestMessage(guestUserId).block().getHead().getStatus()==1;
    }
}
