package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Suhuyuan
 */
@Data
public class LifangConfiguration implements Serializable {
    private String secret;
    private String baseUrl;
}