package com.parkinglotmiddleware.api.dubbo.exposer;

import java.util.Set;

/**
 * @author Suhuyuan
 */
public interface ITestService {
     int add(int a,int b);

     Set<String> add(String item);

     Boolean exception();
}
