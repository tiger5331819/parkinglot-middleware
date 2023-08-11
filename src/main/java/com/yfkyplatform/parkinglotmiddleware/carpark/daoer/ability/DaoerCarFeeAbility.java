package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarFee;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrearByCharge;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseRespHead;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.*;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
            if (redis.check("order:daoer:fee:" + carNo)) {

                String jsonStr = redis.get("order:daoer:fee:" + carNo);
                result = JSONUtil.toBean(jsonStr, CarFeeResult.class);
            } else {
                result = new CarFeeResult();
                result.setCarNo(resp.getHead().getMessage());
                result.setAmount(new BigDecimal(0));
                result.setPayCharge(new BigDecimal(0));
                result.setDiscountAmount(new BigDecimal(0));
            }
        }
        return carFeeToCarOrder(result);
    }

    /**
     * 获取临时车缴纳金额（支持欠费）
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderWithArrearResultByList getCarFeeInfoWithArrear(String carNo) {
        DaoerBaseResp<CarFeeResultWithArrear> resp = api.getCarFeeInfoWithArrear(carNo).block();
        CarFeeResultWithArrearByCharge result = resp.getBody().getCharge();
        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            if (redis.check("order:daoer:fee:" + carNo)) {
                String jsonStr = redis.get("order:daoer:fee:" + carNo);
                result = JSONUtil.toBean(jsonStr, CarFeeResultWithArrearByCharge.class);
            } else {
                result = new CarFeeResultWithArrearByCharge();
                result.setCarNo(resp.getHead().getMessage());
                result.setAmount(new BigDecimal(0));
                result.setPayCharge(new BigDecimal(0));
                result.setDiscountAmount(new BigDecimal(0));
            }
        }
        return carFeeToCarOrder(result, resp.getBody().getArrears());
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
            redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofMinutes(1));
            redis.set("order:daoer:fee:" + result.getCarNo(), JSONUtil.toJsonStr(result), Duration.ofMinutes(2));
        }
        return carFeeToCarOrder(result);
    }

    /**
     * 根据通道号获取车辆费用信息（支持欠费）
     *
     * @param channelId
     * @return
     */
    @Override
    public CarOrderWithArrearResultByList getCarFeeInfoByChannelWithArrear(String channelId) {
        CarFeeResultWithArrear resp = api.getChannelCarFeeWithArrear(channelId).block().getBody();
        CarFeeResultWithArrearByCharge result = ObjectUtil.isNull(resp) ? new CarFeeResultWithArrearByCharge() : resp.getCharge();
        if (StrUtil.isBlank(result.getCarNo())) {
            result = new CarFeeResultWithArrearByCharge();
            result.setOutTime(LocalDateTime.now());
            result.setInTime(LocalDateTime.now());
            result.setInId("");
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        } else {
            redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofMinutes(1));

            redis.set("order:daoer:fee:" + result.getCarNo(), JSONUtil.toJsonStr(result), Duration.ofMinutes(2));
        }
        return carFeeToCarOrder(result, ObjectUtil.isNull(resp) ? null : resp.getArrears());
    }

    /**
     * 无牌车出场（支持欠费）
     *
     * @param openId
     * @param scanType
     * @param channelId
     * @return
     */
    @Override
    public CarOrderWithArrearResultByList blankCarOutWithArrear(String openId, int scanType, String channelId) {
        CarFeeResultWithArrear resp = api.blankCarOutWithArrear(openId, scanType, channelId).block().getBody();
        CarFeeResultWithArrearByCharge result = resp.getCharge();
        if (ObjectUtil.isNull(result) || StrUtil.isBlank(result.getCarNo())) {
            result = new CarFeeResultWithArrearByCharge();
            result.setAmount(new BigDecimal(0));
            result.setPayCharge(new BigDecimal(0));
            result.setDiscountAmount(new BigDecimal(0));
        } else {
            redis.set("order:daoer:" + result.getCarNo(), channelId, Duration.ofMinutes(3));
        }
        return carFeeToCarOrder(result, resp.getArrears());
    }



    /**
     * 临停缴费支付完成（支持欠费）
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccessWithArrear(CarOrderPayMessageWithArrear payMessage) {
        String key = "order:daoer:" + payMessage.getCarNo();
        String inKey = "order:daoer:" + payMessage.getInId();
        String channelId = redis.check(key) ? redis.get(key) : "";
        String parkNo = redis.check(inKey) ? redis.getWithDelete(inKey) : "";

        Mono<DaoerBaseResp<CarFeeResultWithArrear>> mono = api.getCarFeeInfoWithArrear(payMessage.getCarNo());
        CarFeeResultWithArrear carFeeResultWithArrear = mono.block().getBody();
        CarFeeResultWithArrearByCharge fee = null;

        log.info(carFeeResultWithArrear.toString());

        if (ObjectUtil.isNotNull(carFeeResultWithArrear) && ObjectUtil.isNotNull(carFeeResultWithArrear.getCharge()) || ObjectUtil.isNotNull(carFeeResultWithArrear.getArrears())) {
            log.info("inId:" + payMessage.getInId());
            fee = findCarFee(carFeeResultWithArrear, payMessage.getInId());
        }

        boolean redisOrderCheck = redis.check("order:daoer:fee:" + payMessage.getCarNo());
        if (ObjectUtil.isNull(fee) && !redisOrderCheck) {
            log.error("道尔订单不存在：" + payMessage);
            return false;
        } else if (redisOrderCheck) {
            String jsonStr = redis.get("order:daoer:fee:" + payMessage.getCarNo());
            fee = JSONUtil.toBean(jsonStr, CarFeeResultWithArrearByCharge.class);
        }


        Duration duration = Duration.between(fee.getInTime(), fee.getOutTime());

        int payType = changePayType(payMessage.getPayType());

        BigDecimal totalFee = payMessage.getPayFee().add(payMessage.getDiscountFee());

        log.info("ToltalFee:" + totalFee.movePointLeft(2));
        DaoerBaseRespHead payState = api.payCarFeeAccessWithArrear(payMessage.getCarNo(),
                fee.getInTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                new Long(duration.toMinutes()).intValue(),
                totalFee.movePointLeft(2),
                payMessage.getDiscountFee().movePointLeft(2),
                0,
                payType,
                payMessage.getPaymentTransactionId(),
                payMessage.getPayFee().movePointLeft(2),
                channelId,
                payMessage.getInId(),
                parkNo).block().getHead();

        if (payState.getStatus() == 1) {
            return true;
        } else {
            log.error(payState.toString());
            return false;
        }
    }

    private CarFeeResultWithArrearByCharge findCarFee(CarFeeResultWithArrear carFeeResultWithArrear, String inId) {
        CarFeeResultWithArrearByCharge charge = carFeeResultWithArrear.getCharge();
        if (StrUtil.equals(charge.getInId(), inId)) {
            return charge;
        }

        return carFeeResultWithArrear.getArrears()
                .stream().filter(item -> StrUtil.equals(item.getInId(), inId))
                .findFirst()
                .orElse(null);
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
        String channelId = redis.check(key) ? redis.get(key) : "";

        Mono<DaoerBaseResp<CarFeeResult>> mono = api.getCarFeeInfo(payMessage.getCarNo());

        int payType = changePayType(payMessage.getPayType());

        CarFeeResult fee = mono.block().getBody();

        boolean redisOrderCheck = redis.check("order:daoer:fee:" + payMessage.getCarNo());
        if (ObjectUtil.isNull(fee) && !redisOrderCheck) {
            log.error("道尔订单不存在：" + payMessage);
            return false;
        } else if (redisOrderCheck) {
            String jsonStr = redis.get("order:daoer:fee:" + payMessage.getCarNo());
            fee = JSONUtil.toBean(jsonStr, CarFeeResult.class);
        }

        BigDecimal totalFee = payMessage.getPayFee().add(payMessage.getDiscountFee());

        log.info(fee.toString());

        log.info("ToltalFee:" + totalFee.movePointLeft(2));
        DaoerBaseRespHead payState = api.payCarFeeAccess(payMessage.getCarNo(),
                fee.getInTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                fee.getChargeDuration(),
                totalFee.movePointLeft(2),
                payMessage.getDiscountFee().movePointLeft(2),
                0,
                payType,
                payMessage.getPaymentTransactionId(),
                payMessage.getPayFee().movePointLeft(2),
                channelId).block().getHead();

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

        Duration duration = StrUtil.isBlank(carFeeResult.getCarNo()) ? Duration.ZERO : Duration.between(carFeeResult.getInTime(), carFeeResult.getOutTime());

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
        orderResult.setOutTime(carFeeResult.getOutTime());

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
