package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Suhuyuan
 */
@Data
public class DaoerConfiguration implements Serializable {
    private String appName;
    private String parkId;
    private String baseUrl;
    private String imgUrl;
    private Boolean backTrack;

}