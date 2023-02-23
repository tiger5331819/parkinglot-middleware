package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangParkingLotWebClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangTool;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.CarFeePay;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.CarNo;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.nio.charset.Charset;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class LifangParkingLotClient extends LifangParkingLotWebClient implements ILifangCarPort, ILifangTool {

    private final ObjectMapper mapper = new ObjectMapper();

    public LifangParkingLotClient(String secret, String baseUrl) {
        super(secret, baseUrl);
    }

    private <T> T decodeData(byte[] resultBytes, Class<T> dataClass) {
        String json = new String(resultBytes, Charset.forName("GBK"));
        try {
            return mapper.readValue(json, dataClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取车位使用情况
     *
     * @return
     */
    @Override
    public CarportResult getCarPortInfo() {

        byte[] requestBytes = post(null, "/GetParkingLotInfo", byte[].class).block();

        return decodeData(requestBytes, CarportResult.class);
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarFeeResult getCarFeeInfo(String carNo) {
        CarNo model = new CarNo();
        model.setCarCode(carNo);
        byte[] requestBytes = post(model, "/GetCarInfo", byte[].class).block();
        return decodeData(requestBytes, CarFeeResult.class);
    }

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public LifangBaseResp payCarFeeAccess(String carNo, String payTime, BigDecimal totalAmount, BigDecimal disAmount,
                                          String paySource, int payType, BigDecimal couponAmount) {
        CarFeePay model = new CarFeePay();
        model.setPayTime(payTime);
        model.setCarCode(carNo);
        model.setChargeMoney(totalAmount);
        model.setJMMoney(couponAmount);
        model.setChargeSource(paySource);
        model.setChargeType(payType);
        model.setPaidMoney(disAmount);

        byte[] requestBytes = post(model, "/AddChargeInfo", byte[].class).block();
        return decodeData(requestBytes, LifangBaseResp.class);
    }

    /**
     * 获取Token
     *
     * @return
     */
    @Override
    public String getSecret() {
        return secret;
    }
}
