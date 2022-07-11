package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.LifangWebClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangTool;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.LifangBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.carpark.CarFeePay;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

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
    public Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo() {
        LifangBase model = new LifangBase("api/index/carport");
        return post(model, new ParameterizedTypeReference<DaoerBaseResp<List<CarportResult>>>() {
        });
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo) {
        LifangBase model = new LifangBase("api/index/tempFee/getcarfee");
        model.setCarNo(carNo);
        return post(model, new ParameterizedTypeReference<DaoerBaseResp<CarFeeResult>>() {
        });
    }

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> payCarFeeAccess(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount,
                                               int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId) {
        CarFeePay model = new CarFeePay("api/index/tempFee/paysuccess");

        model.setEntryTime(entryTime);
        model.setCarNo(carNo);
        model.setPayTime(payTime);
        model.setDuration(duration);
        model.setTotalAmount(totalAmount);
        model.setDisAmount(disAmount);
        model.setPaymentType(paymentType);
        model.setPayType(payType);
        model.setPaymentTnx(paymentTnx);
        model.setCouponAmount(couponAmount);
        model.setDsn(channelId);

        return post(model, DaoerBaseResp.class);
    }

    /**
     * 获取Token
     *
     * @return
     */
    @Override
    public String getSecret() {
        return null;
    }
}
