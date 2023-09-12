package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarFee;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrearByCharge;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseRespHead;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.*;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 道尔费用能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerCarFeeAbility implements ICarFeeAblitity {

    private final IDaoerCarFee api;

    private final RedisTool redis;

    private final DaoerParkingLotConfiguration configuration;

    public DaoerCarFeeAbility(IDaoerCarFee daoerClient, DaoerParkingLotConfiguration configuration, RedisTool redis) {
        this.api = daoerClient;
        this.redis = redis;
        this.configuration = configuration;
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        if (configuration.getBackTrack()) {
            DaoerBaseResp<CarFeeResultWithArrear> resp;
            if(redis.check("order:daoer:backTrack" + carNo)){
                resp=redis.get("order:daoer:backTrack" + carNo);
            }else{
                resp = api.getCarFeeInfoWithArrear(carNo).block();
            }
            CarFeeResultWithArrearByCharge result = checkCarFeeWithArrearResult(resp);
            return carFeeToCarOrder(result, resp.getBody().getArrears());
        } else {
            DaoerBaseResp<CarFeeResult> resp = api.getCarFeeInfo(carNo).block();
            CarFeeResult result = checkCarFeeResult(resp);
            return carFeeToCarOrder(result);
        }
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelId 通道Id
     * @param scanType  1微信2支付宝
     * @param openId    openId
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfoByChannel(String channelId, int scanType, String openId) {
        if (configuration.getBackTrack()) {
            DaoerBaseResp<CarFeeResultWithArrear> resp = api.blankCarOutWithArrear(openId, scanType, channelId).block();
/*            if (ObjectUtil.isNull(resp.getBody()) || StrUtil.isBlank(resp.getBody().getCharge().getCarNo())) {
                resp = api.blankCarOutWithArrear(openId, scanType, channelId).block();
            }*/
            CarFeeResultWithArrearByCharge result = checkCarFeeWithArrearResult(resp);

            if (StrUtil.isNotBlank(result.getCarNo())) {
                redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofMinutes(1));
                redis.set("order:daoer:backTrack" + result.getCarNo(), resp, Duration.ofMinutes(2));
            }

            return carFeeToCarOrder(result, ObjectUtil.isNull(resp.getBody()) ? null : resp.getBody().getArrears());
        } else {
            DaoerBaseResp<CarFeeResult> resp = api.getChannelCarFee(channelId, null, openId).block();
            CarFeeResult result = checkCarFeeResult(resp);

            if (StrUtil.isNotBlank(result.getCarNo())) {
                redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofMinutes(1));
            }
            return carFeeToCarOrder(result);
        }
    }

    private CarFeeResultWithArrearByCharge checkCarFeeWithArrearResult(DaoerBaseResp<CarFeeResultWithArrear> resp) {
        log.info("道尔费用:" + resp);
        CarFeeResultWithArrearByCharge result = ObjectUtil.isNull(resp.getBody()) ? null : resp.getBody().getCharge();
        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            log.info(resp.getHead().getMessage());
            result = new CarFeeResultWithArrearByCharge();
            result.setOutTime(LocalDateTime.now());
            result.setInTime(LocalDateTime.now());
            result.setInId("");
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        }

        return result;
    }

    private CarFeeResult checkCarFeeResult(DaoerBaseResp<CarFeeResult> resp) {
        log.info("道尔费用:" + resp);
        CarFeeResult result = resp.getBody();
        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            log.info(resp.getHead().getMessage());
            result = new CarFeeResult();
            result.setCarNo("");
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        }

        return result;
    }

    /**
     * 临停缴费支付完成
     * (支持欠费)
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        String key = "order:daoer:" + payMessage.getCarNo();
        String channelId = redis.check(key) ? redis.get(key) : "";

        String inKey = "order:daoer:" + payMessage.getInId();
        String parkNo = redis.check(inKey) ? redis.getWithDelete(inKey) : "";

        Duration duration = Duration.between(payMessage.getInTime(), payMessage.getCreateTime());
        int payType = changePayType(payMessage.getPayType());
        BigDecimal totalFee = payMessage.getPayFee().add(payMessage.getDiscountFee()).movePointLeft(2);
        log.info("TotalFee:" + totalFee);

        DaoerBaseRespHead payState;
        if (configuration.getBackTrack()) {
            payState = api.payCarFeeAccessWithArrear(payMessage.getCarNo(),
                    payMessage.getInTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new Long(duration.toMinutes()).intValue(),
                    totalFee,
                    payMessage.getDiscountFee().movePointLeft(2),
                    0,
                    payType,
                    payMessage.getPaymentTransactionId(),
                    payMessage.getPayFee().movePointLeft(2),
                    channelId,
                    payMessage.getInId(),
                    parkNo).block().getHead();
        } else {
            payState = api.payCarFeeAccess(payMessage.getCarNo(),
                    payMessage.getInTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new Long(duration.toMinutes()).intValue(),
                    totalFee,
                    payMessage.getDiscountFee().movePointLeft(2),
                    0,
                    payType,
                    payMessage.getPaymentTransactionId(),
                    payMessage.getPayFee().movePointLeft(2),
                    channelId).block().getHead();
        }

        if (payState.getStatus() == 1) {
            return true;
        } else {
            log.error(payState.getMessage());
            return false;
        }
    }

    private int changePayType(int payType) {
        switch (payType) {
            case 2000:
            case 2001:
                return 1;
            case 3000:
            case 3001:
                return 2;
            default:
                return 0;
        }
    }

    private CarOrderResult carFeeToCarOrder(CarFeeResult carFeeResult) {
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

    private CarOrderWithArrearResult carFeeToCarOrder(CarFeeResultWithArrearByCharge carFeeResult) {
        CarOrderWithArrearResultByList orderResult = new CarOrderWithArrearResultByList();

        Duration duration = ObjectUtil.isNull(carFeeResult.getInTime()) || ObjectUtil.isNull(carFeeResult.getOutTime()) ?
                Duration.ZERO : Duration.between(carFeeResult.getInTime(), carFeeResult.getOutTime());

        orderResult.setCarNo(carFeeResult.getCarNo());
        orderResult.setDiscountFee(carFeeResult.getDiscountAmount().movePointRight(2));
        orderResult.setPayFee(carFeeResult.getPayCharge().movePointRight(2));
        orderResult.setTotalFee(carFeeResult.getAmount().movePointRight(2));
        orderResult.setCreateTime(carFeeResult.getOutTime());
        orderResult.setStartTime(carFeeResult.getInTime());
        orderResult.setServiceTime(new Long(duration.toMinutes()).intValue());

        orderResult.setOverTime(carFeeResult.getOverTime());
        orderResult.setPaymentType(carFeeResult.getPaymentType());
        orderResult.setParkingNo(carFeeResult.getParkingNo());
        orderResult.setInId(carFeeResult.getInId());

        redis.set("order:daoer:" + carFeeResult.getInId(), carFeeResult.getParkingNo(), Duration.ofMinutes(3));

        return orderResult;
    }

    private CarOrderWithArrearResultByList carFeeToCarOrder(CarFeeResultWithArrearByCharge carFeeResult, List<CarFeeResultWithArrearByCharge> arrears) {
        CarOrderWithArrearResultByList orderResult = (CarOrderWithArrearResultByList) carFeeToCarOrder(carFeeResult);

        if (AssertTool.checkCollectionNotNull(arrears)) {
            orderResult.setArrearList(arrears.stream().map(item -> (CarOrderWithArrearResultByList) carFeeToCarOrder(item)).collect(Collectors.toList()));
        }

        return orderResult;
    }

}
