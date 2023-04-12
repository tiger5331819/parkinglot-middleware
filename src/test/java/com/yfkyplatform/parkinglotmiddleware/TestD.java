package com.yfkyplatform.parkinglotmiddleware;

import lombok.Value;

/**
 * @author Suhuyuan
 */
@Value
public class TestD {
    private Integer id;

    private String name;

    public static TestD make() {
        return new TestD(1, "123");
    }

    public String getName() {
        return "ceshi";
    }
}
