package com.yfkyplatform.parkinglotmiddleware.domain.repository.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @author Suhuyuan
 */
@Data
@ConfigurationProperties(prefix = "parking-lot-config")
public class ParkingLotConfig {
    private List<Map<String, String>> Daoer;

}
