package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglotmiddleware.universal.web.WebRequestBase;
import com.yfkyplatform.parkinglotmiddleware.universal.web.YfkyWebClient;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangAES.encrypt;
import static com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangAES.parseByte2HexStr;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class LifangWebClient extends YfkyWebClient {
    protected String secret;

    public LifangWebClient(String secret, String baseUrl) {
        super(baseUrl, 3);
        this.secret = secret;
    }

    private <T extends WebRequestBase> String encryptData(T data) {
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
