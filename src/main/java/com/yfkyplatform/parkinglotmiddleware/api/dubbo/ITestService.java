package com.yfkyplatform.parkinglotmiddleware.api.dubbo;

import java.util.Set;

/**
 * @author Suhuyuan
 */
public interface ITestService {
     int add(int a,int b);

     Set<String> add(String item);

     Boolean exception();
}
