package com.parkinglotmiddleware.universal.extension;

/**
 * 拓展接口
 *
 * @author Suhuyuan
 */

public interface IExtensionFuction {
    default void setToken() {
    }

    default boolean getToken() {
        return false;
    }

    default void removeToken() {
    }

}
