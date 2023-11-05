package com.parkinglotmiddleware.carpark.hongmen.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglotmiddleware.carpark.hongmen.client.domin.HongmenWebClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class HongmenParkingLotClient extends HongmenWebClient {

    private final ObjectMapper mapper = new ObjectMapper();

    public HongmenParkingLotClient(String appId, String secret, String baseUrl) {
        super(appId, secret, baseUrl);
    }

}
