package com.parkinglotmiddleware.carpark.lifang.client.domin;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkinglotmiddleware.universal.web.ParkingLotWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * 立方云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class LifangWebClient extends ParkingLotWebClient {
    protected String secret;

    protected String appId;

    public LifangWebClient(String secret, String baseUrl, String appId) {
        super(baseUrl, 3);
        this.secret = secret;
        this.appId = appId;
    }

    /**
     * 按照第一个字符的键值 ASCII 码递增排序（字母升序排序）
     *
     * @param str
     * @return
     */
    private String ASCIISort(String str) {
        char[] sortCharNum = str.toCharArray();
        Arrays.sort(sortCharNum);
        return new String(sortCharNum).trim();
    }

    /**
     * 对字符串进行
     *
     * @param str
     * @return
     */
    private String signData(String str) {
        byte[] encryptData = str.getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA, secret, null);
        byte[] signed = sign.sign(encryptData);
        return Base64.encode(signed);
    }

    private <T> String signDataAndMakeUrl(String method, T data) {
        ObjectMapper mapper = new ObjectMapper();
        String dataStr;
        try {
            dataStr = mapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("?method=").append(method)
                .append("&data=").append(dataStr)
                .append("&app_id=").append(appId);
        //.append("&timestamp=").append(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());

        String signStr = signData(builder.toString());

        builder.append("&sign=").append(signStr);

        return builder.toString();
    }

    @Override
    protected <R, T> Mono<R> post(T data, String url, ParameterizedTypeReference<R> result) {
        return super.post(data, url, result);
    }

    @Override
    protected <R, T> Mono<R> post(T data, String url, Class<R> result) {
        return super.post(data, url, result);
    }
}
