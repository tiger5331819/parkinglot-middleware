package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarFee;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseRespHead;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.ICarFeeAblitity;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * 道尔费用能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerCarFeeAbility implements ICarFeeAblitity {

    private final IDaoerCarFee api;

    private final RedisTool redis;

    public DaoerCarFeeAbility(IDaoerCarFee daoerClient, RedisTool redis) {
        api = daoerClient;
        this.redis = redis;
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        DaoerBaseResp<CarFeeResult> resp = api.getCarFeeInfo(carNo).block();
        CarFeeResult result = resp.getBody();
        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            result = new CarFeeResult();
            result.setCarNo(resp.getHead().getMessage());
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        }
        return CarFeeToCarOrder(result);
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelId 通道Id
     * @param carNo     车牌号码
     * @param openId    openId
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String channelId, String carNo, String openId) {
        CarFeeResult result = api.getChannelCarFee(channelId, carNo, openId).block().getBody();


        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            result = new CarFeeResult();
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        } else {
            redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofHours(1));
        }
        return CarFeeToCarOrder(result);
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelId
     * @param carNo     车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String channelId, String carNo) {
        return null;
    }

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        String key = "order:daoer:" + payMessage.getCarNo();
        String channelId = redis.check(key) ? redis.getWithDelete(key) : "";

        Mono<DaoerBaseResp<CarFeeResult>> mono = api.getCarFeeInfo(payMessage.getCarNo());

        int payType;
        switch (payMessage.getPayType()) {
            case 2000:
            case 2001:
                payType = 1;
                break;
            case 3000:
            case 3001:
                payType = 2;
                break;
            default:
                payType = 0;
        }

        CarFeeResult fee = mono.block().getBody();

        BigDecimal totalFee = payMessage.getPayFee().add(payMessage.getDiscountFee());

        log.info("ToltalFee:" + totalFee);


        DaoerBaseRespHead payState = api.payCarFeeAccess(payMessage.getCarNo(),
                new DateTime(fee.getInTime()).toString(),
                payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                fee.getChargeDuration(),
                totalFee.movePointLeft(2),
                payMessage.getDiscountFee().movePointLeft(2),
                0,
                payType,
                payMessage.getPaymentTransactionId(),
                payMessage.getPayFee().movePointLeft(2),
                channelId).block().getHead();
        log.info("ToltalFee:" + totalFee.movePointLeft(2));
        if (payState.getStatus() == 1) {
            return true;
        } else {
            log.error(payState.getMessage());
            return false;
        }
    }

    private CarOrderResult CarFeeToCarOrder(CarFeeResult carFeeResult) {
        CarOrderResult orderResult = new CarOrderResult();

        orderResult.setCarNo(carFeeResult.getCarNo());
        orderResult.setDiscountFee(carFeeResult.getDiscountAmount().movePointRight(2));
        orderResult.setPayFee(carFeeResult.getPayCharge().movePointRight(2));
        orderResult.setTotalFee(carFeeResult.getAmount().movePointRight(2));
        orderResult.setCreateTime(carFeeResult.getChargeTime());
        orderResult.setStartTime(carFeeResult.getInTime());
        orderResult.setServiceTime(carFeeResult.getChargeDuration());

        return orderResult;
    }
}
