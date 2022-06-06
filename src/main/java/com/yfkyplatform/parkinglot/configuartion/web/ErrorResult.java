package com.yfkyplatform.parkinglot.configuartion.web;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 错误输出
 *
 * @author Suhuyuan
 */
@Data
@AllArgsConstructor
public class ErrorResult {
    private int code;
    private String msg;
}
