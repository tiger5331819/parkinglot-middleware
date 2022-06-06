package com.yfkyplatform.parkinglot.domain.manager.container;

import com.yfkyplatform.parkinglot.domain.manager.ParkingLotConfiguration;

/**
 * 停车场容器
 *
 * @author Suhuyuan
 */

public abstract class ParkingLotPod {

    protected ParkingLotConfiguration cfg;

    public ParkingLotPod(ParkingLotConfiguration parkingLotConfiguration){
        this.cfg= parkingLotConfiguration;
    }


    /**
     * 获取驱动
     * @return
     * @param <T>
     */
    public abstract <T> T client();

    /**
     * 获取配置信息
     * @return
     * @param <T>
     */
    public <T extends ParkingLotConfiguration> T configuration(){
        return (T) cfg;
    }

    /**
     * 健康检查
     * @return
     */
    public abstract boolean healthCheck();

    /**
     * 获取图片
     * @param imgPath 图片路径
     * @return
     */
    public abstract byte[] getImage(String imgPath);
}
