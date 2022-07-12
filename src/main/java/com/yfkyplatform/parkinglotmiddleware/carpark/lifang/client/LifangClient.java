package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangWebClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangTool;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.CarFeePay;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.CarNo;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.LifangBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class LifangClient extends LifangWebClient implements ILifangCarPort, ILifangTool {

    public LifangClient(String secret, String baseUrl, RedisTool redisTool) {
        super(secret, baseUrl, redisTool);
    }

    /**
     * 获取车位使用情况
     *
     * @return
     */
    @Override
    public Mono<CarportResult> getCarPortInfo() {
        LifangBase model = new LifangBase("/GetParkingLotInfo");
        return post(model, new ParameterizedTypeReference<CarportResult>() {
        });
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<CarFeeResult> getCarFeeInfo(String carNo) {
        CarNo model = new CarNo("/GetCarInfo");
        model.setCarCode(carNo);
        return post(model, new ParameterizedTypeReference<CarFeeResult>() {
        });
    }

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<LifangBaseResp> payCarFeeAccess(String carNo, String payTime, BigDecimal totalAmount, BigDecimal disAmount,
                                                String paySource, int payType, BigDecimal couponAmount) {
        CarFeePay model = new CarFeePay("/AddChargeInfo");
        model.setPayTime(payTime);
        model.setCarCode(carNo);
        model.setChargeMoney(totalAmount);
        model.setJMMoney(couponAmount);
        model.setChargeSource(paySource);
        model.setChargeType(payType);
        model.setPaidMoney(disAmount);

        return post(model, LifangBaseResp.class);
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
