package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.model.HongmenBase;
import com.yfkyplatform.parkinglotmiddleware.universal.web.ParkingLotWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 立方云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class HongmenWebClient extends ParkingLotWebClient {
    protected String secret;

    protected String appId;

    public HongmenWebClient(String appId, String secret, String baseUrl) {
        super(baseUrl, 3);
        this.secret = secret;
        this.appId = appId;
    }


    /**
     * 签名
     *
     * @param data
     * @return
     */
    private <Data extends HongmenBase> String signData(Data data) {
        return SecureUtil.md5(data.getAppId() + data.getRequestId() + data.getType() + secret + data.getTimestamp()).toLowerCase();
    }

    private <Data extends HongmenBase> void makeData(Data data) {
        data.setAppId(appId);
        data.setRequestId(IdUtil.simpleUUID());
        data.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }

    @Override
    protected <R, T> Mono<R> post(T data, String url, ParameterizedTypeReference<R> result) {
        HongmenBase hongmenData = (HongmenBase) data;
        makeData(hongmenData);
        hongmenData.setSign(signData(hongmenData));
        return super.post(hongmenData, url, result);
    }

    @Override
    protected <R, T> Mono<R> post(T data, String url, Class<R> result) {
        HongmenBase hongmenData = (HongmenBase) data;
        makeData(hongmenData);
        hongmenData.setSign(signData(hongmenData));
        return super.post(hongmenData, url, result);
    }
}
