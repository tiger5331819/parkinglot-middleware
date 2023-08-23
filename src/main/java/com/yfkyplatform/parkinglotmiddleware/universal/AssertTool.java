package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;

import java.util.Collection;

/**
 * 通用断言
 *
 * @author Suhuyuan
 */

public class AssertTool {

    public static boolean checkCarNotNull(Car car) {
        return ObjectUtil.isNotNull(car) && StrUtil.isNotBlank(car.getCarNo());
    }

    public static boolean checkCollectionNotNull(Collection collection) {
        return ObjectUtil.isNotNull(collection) && !collection.isEmpty();
    }
}
