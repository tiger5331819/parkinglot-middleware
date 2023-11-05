package com.parkinglotmiddleware.carpark.jieshun.client;

import lombok.extern.slf4j.Slf4j;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunClient {//extends JieShunWebClient implements IJieShunCarPort, IJieShunCarFee, IJieShunMonthlyCar, IJieShunTool {

    public JieShunClient(String baseUrl, int readTimeOutSeconds) {
        //super(baseUrl,readTimeOutSeconds);
    }

    /**
     * 获取车位使用情况
     *
     * @return
     */
/*    @Override
    public Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo() {
        JieShunBase model = new JieShunBase();
        return post(model, "api/index/carport", new ParameterizedTypeReference<DaoerBaseResp<List<CarportResult>>>() {
        });
    }*/


}
