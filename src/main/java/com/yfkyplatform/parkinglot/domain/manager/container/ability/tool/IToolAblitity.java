package com.yfkyplatform.parkinglot.domain.manager.container.ability.tool;

/**
 * 工具接口
 *
 * @author Suhuyuan
 */
public interface IToolAblitity {
    /**
     * 获取Token
     *
     * @return
     */
    String getToken();
    /**
     * 获取图片
     * @param imgPath 图片路径
     * @return
     */
    byte[] getImage(String imgPath);
}
