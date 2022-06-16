package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer;

import com.yfkyframework.common.core.exception.ExposerException;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class TestServiceExposer implements ITestService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public Set<String> add(String item) {
        Set<String> data = new HashSet();
        data.add(item);
        data.add(item+"123");
        data.add(item+"over");
        return data;

    }

    @Override
    public Boolean exception() {
        throw new ExposerException(1,"123");
    }
}
