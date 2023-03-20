package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Suhuyuan
 */
@Data
public class HongmenConfiguration implements Serializable {

    private String appId;
    private String secret;
    private String baseUrl;
}