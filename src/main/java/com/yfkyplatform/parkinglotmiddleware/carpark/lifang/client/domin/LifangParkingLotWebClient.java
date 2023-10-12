package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglotmiddleware.universal.web.ParkingLotWebClient;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangAES.encrypt;
import static com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangAES.parseByte2HexStr;

/**
 * 立方线下车场WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class LifangParkingLotWebClient extends ParkingLotWebClient {
    protected String secret;

    public LifangParkingLotWebClient(String secret, String baseUrl) {
        super(baseUrl, 3);
        this.secret = secret;
    }

    private <T> String encryptData(T data) {
        ObjectMapper mapper = new ObjectMapper();
        String dataStr;
        byte[] encryptData;
        try {
            dataStr = mapper.writeValueAsString(data);
            encryptData = encrypt(dataStr.getBytes(StandardCharsets.UTF_8), secret, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return parseByte2HexStr(encryptData);
    }
}
