package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.guest;

/**
 *访客接口
 *
 * @author Suhuyuan
 */
public interface IGuestAblitity {
    /**
     * 创建访客
     * @return
     */
    GuestUserResult createGuest(GuestMessage guestMessage);

    /**
     * 修改访客
     *
     * @return
     */
    Boolean changeGuestMessage(String guestUserId, GuestMessage guestMessage);

    /**
     * 删除访客
     *
     * @return
     */
    Boolean removeGuestMessage(String guestUserId);
}
