package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.util.ObjectUtil;

import java.util.Collection;

/**
 * 通用断言
 *
 * @author Suhuyuan
 */

public class AssertTool {

    public static boolean checkEntityNotNull(Object object) {
        return ObjectUtil.isNotNull(object);
    }

    public static boolean checkCollectionNotNull(Collection collection) {
        return ObjectUtil.isNotNull(collection) && !collection.isEmpty();
    }
}
