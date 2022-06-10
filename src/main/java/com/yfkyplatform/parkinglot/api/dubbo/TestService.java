package com.yfkyplatform.parkinglot.api.dubbo;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * 测试服务
 *
 * @author Suhuyuan
 */
@DubboService
public class TestService implements ITestService{

    @Override
    public int Add(int a,int b){
        return a+b;
    }
}
