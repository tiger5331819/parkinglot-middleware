package com.parkinglotmiddleware.carpark.daoer.client;

import com.parkinglotmiddleware.carpark.daoer.client.domin.DaoerWebClient;
import com.parkinglotmiddleware.carpark.daoer.client.domin.api.*;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.carfee.CarFeePay;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.carfee.CarFeePayWithArrear;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.carfee.ChannelCarFee;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.carfee.ChannelCarFeeWithArrear;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark.*;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon.CouponQuery;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon.CouponUse;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.guest.GuestMessage;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.guest.GuestWithId;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar.CreateMonthlyCar;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar.LockMonthlyCar;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar.RenewalMonthlyCar;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.guest.GuestResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.SpecialMonthlyCarResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.BlankCarURL;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.CarOutPayURL;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.URLResult;
import com.parkinglotmiddleware.universal.RedisTool;
import com.parkinglotmiddleware.universal.testbox.TestBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerClient extends DaoerWebClient implements IDaoerCarPort, IDaoerCarFee, IDaoerMonthlyCar, IDaoerGuest, IDaoerCoupon, IDaoerTool {

    public DaoerClient(String id, String appName, String parkId, String baseUrl, String imgUrl, RedisTool redisTool, int readTimeOutSeconds, TestBox testBox) {
        super(id, appName, parkId, baseUrl, imgUrl, redisTool,testBox,readTimeOutSeconds);
    }

    /**
     * 获取车位使用情况
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo() {
        DaoerBase model = new DaoerBase();
        return post(model, "api/index/carport", new ParameterizedTypeReference<DaoerBaseResp<List<CarportResult>>>() {
        });
    }

    /**
     * 获取临时车缴纳金额
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);
        return post(model, "api/index/tempFee/getcarfee", new ParameterizedTypeReference<DaoerBaseResp<CarFeeResult>>() {
        });
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResultWithArrear>> getCarFeeInfoWithArrear(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);
        return post(model, "api/index/tempFee/ly/getcarfee", new ParameterizedTypeReference<DaoerBaseResp<CarFeeResultWithArrear>>() {
        });
    }

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> payCarFee(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount,
                                               int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId) {
        CarFeePay model = new CarFeePay();

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

        log.info("道尔支付参数：" + model);

        return post(model, "api/index/tempFee/paysuccess", DaoerBaseResp.class);
    }

    /**
     * 临停缴费支付完成（支持欠费）
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> payCarFeeWithArrear(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount, int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId, String inId, String parkNo) {
        CarFeePayWithArrear model = new CarFeePayWithArrear();

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
        model.setInId(inId);
        model.setParkId(parkNo);

        log.info("道尔支付参数：" + model);

        return post(model, "api/index/tempFee/ly/paysuccess", DaoerBaseResp.class);
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResult>> getChannelCarFee(String channelId, String carNo, String openId) {
        ChannelCarFee model = new ChannelCarFee();

        model.setCarNo(carNo);
        model.setDsn(channelId);
        model.setOpenId(openId);

        return post(model, "api/index/tempFee/getCarBayDsn", new ParameterizedTypeReference<DaoerBaseResp<CarFeeResult>>() {
        });
    }

    /**
     * 根据通道号获取车辆费用信息（支持欠费）
     *
     * @param channelId
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResultWithArrear>> getChannelCarFeeWithArrear(String channelId) {
        ChannelCarFeeWithArrear model = new ChannelCarFeeWithArrear();

        model.setDsn(channelId);

        return post(model, "api/index/tempFee/ly/getCarByDsn", new ParameterizedTypeReference<DaoerBaseResp<CarFeeResultWithArrear>>() {
        });
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
    public Mono<DaoerBaseResp<CarFeeResultWithArrear>> blankCarOutWithArrear(String openId, int scanType, String channelId) {
        BlankCarInOrOut model = new BlankCarInOrOut();

        model.setChannelId(channelId);
        model.setScanType(scanType);
        model.setOpenId(openId);

        return post(model, "api/index/ly/scanout", new ParameterizedTypeReference<DaoerBaseResp<CarFeeResultWithArrear>>() {
        });
    }

    /**
     * 无牌车入场
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<BlankCarInResult>> blankCarIn(String openId, int scanType, String channelId) {
        BlankCarInOrOut model = new BlankCarInOrOut();

        model.setChannelId(channelId);
        model.setScanType(scanType);
        model.setOpenId(openId);

        return post(model, "api/index/scanin", new ParameterizedTypeReference<DaoerBaseResp<BlankCarInResult>>() {
        });
    }

    /**
     * 无牌车出场
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<BlankCarOutResult>> blankCarOut(String openId, int scanType, String channelId){
        BlankCarInOrOut model = new BlankCarInOrOut();

        model.setChannelId(channelId);
        model.setScanType(scanType);
        model.setOpenId(openId);

        return post(model, "api/index/scanout", new ParameterizedTypeReference<DaoerBaseResp<BlankCarOutResult>>() {
        });
    }

    /**
     * 获取入场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<PageModel<CarInData>>> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        CarInOrOut model = new CarInOrOut();

        model.setCarNo(carNo);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.configPage(pageNum, pageSize);

        return post(model, "api/index/carins", new ParameterizedTypeReference<DaoerBaseResp<PageModel<CarInData>>>() {
        });
    }

    /**
     * 获取出场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<PageModel<CarOutData>>> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        CarInOrOut model = new CarInOrOut();

        model.setCarNo(carNo);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.configPage(pageNum, pageSize);

        return post(model, "api/index/carouts", new ParameterizedTypeReference<DaoerBaseResp<PageModel<CarOutData>>>() {
        });
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelResult>>> getChannelsInfo() {
        DaoerBase model = new DaoerBase();
        return post(model, "api/index/getchannels", new ParameterizedTypeReference<DaoerBaseResp<List<ChannelResult>>>() {
        });
    }

    /**
     * 获取设备状态
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelStateResult>>> getChannelStates() {
        DaoerBase model = new DaoerBase();
        return post(model, "api/index/getchannelstatus", new ParameterizedTypeReference<DaoerBaseResp<List<ChannelStateResult>>>() {
        });
    }

    /**
     * 控制道闸开、关。
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelStatusResult>>> controlChannel(String channelId, int channelIdStatus){
        Channel model = new Channel();
        model.setChannelId(channelId);
        model.setChannelIdStatus(channelIdStatus);
        return post(model, "api/index/onandoff", new ParameterizedTypeReference<DaoerBaseResp<List<ChannelStatusResult>>>() {});
    }

    /**
     * 创建访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<GuestResult>> createGuest(String guestName, String carNo, LocalDateTime visitTime, String mobile, String description){
        GuestMessage model = new GuestMessage();
        model.setGuestName(guestName);
        model.setDescription(description);
        model.setMobile(mobile);
        model.setVisitTime(visitTime);
        model.setCarNo(carNo);

        return post(model, "api/guest/create", new ParameterizedTypeReference<DaoerBaseResp<GuestResult>>() {});
    }

    /**
     * 修改访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> changeGuestMessage(String objectId,String guestName,LocalDateTime visitTime,String mobile,String description){
        GuestMessage model = new GuestWithId(objectId);
        model.setGuestName(guestName);
        model.setDescription(description);
        model.setMobile(mobile);
        model.setVisitTime(visitTime);

        return post(model, "api/guest/update", DaoerBaseResp.class);
    }

    /**
     * 删除访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> removeGuestMessage(String objectId){
        GuestMessage model = new GuestWithId(objectId);

        return post(model, "api/guest/cancel", DaoerBaseResp.class);
    }

    /**
     * 查询优惠券
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<CouponResult>>> getCoupon(String openId){
        CouponQuery model = new CouponQuery();
        model.setOpenId(openId);
        return post(model, "api/index/coupon/getcoupon", new ParameterizedTypeReference<DaoerBaseResp<List<CouponResult>>>() {});
    }

    /**
     * 使用优惠券
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CouponUseResult>> useCoupon(String objectId, String carNo){
        CouponUse model = new CouponUse();
        model.setObjectId(objectId);
        model.setCarNo(carNo);
        return post(model, "api/index/coupon/bindcar", new ParameterizedTypeReference<DaoerBaseResp<CouponUseResult>>() {});
    }

    /**
     * 获取停车场下的月卡费率
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>> getMonthlyCarLongRentalRate(){
        return get("api/index/monthlycar/long-rental-rate" + "/" + parkId, new ParameterizedTypeReference<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>>() {});
    }

    /**
     * 获取月租车基本信息
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> getMonthlyCarInfo(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);

        return post(model, "api/index/monthlycar/info", new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {
        });
    }

    /**
     * 月租车开户
     *
     * @param carNo
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> createMonthlyCar(String carNo, Integer cardType,
                                                                  String startTime, String endTime, String balanceMoney, int payType,
                                                                  String contactName, String concatPhone) {
        CreateMonthlyCar model = new CreateMonthlyCar();
        model.setBalanceMoney(balanceMoney);
        model.setCardTypeId(cardType);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setPayType(payType);
        model.setContactName(contactName);
        model.setConcatPhone(concatPhone);
        model.setCarNo(carNo);

        return post(model, "api/index/monthlycar/create", new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {
        });
    }

    /**
     * 获取月租车缴费历史
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<MonthlyCarHistoryResult>>> getMonthlyCarHistory(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);

        return post(model, "api/index/monthlycar/history", new ParameterizedTypeReference<DaoerBaseResp<List<MonthlyCarHistoryResult>>>() {});
    }

    /**
     * 月租车续期
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> renewalMonthlyCar(String carNo,String newStartTime,String newEndTime,String balanceMoney,int payType){
        RenewalMonthlyCar model = new RenewalMonthlyCar();
        model.setCarNo(carNo);
        model.setNewEndTime(newEndTime);
        model.setNewStartTime(newStartTime);
        model.setBalanceMoney(balanceMoney);
        model.setPayType(payType);
        return post(model, "api/index/monthlycar/newdate", new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {});
    }

    /**
     * 月租车销户
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> removeMonthlyCar(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);
        return post(model, "api/index/monthlycar/del", new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {
        });
    }

    /**
     * 新增修改特殊车辆
     *
     * @param carNo
     * @param carNoType
     * @param isStop
     * @param description
     * @param objectId
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> specialMonthlyCar(String carNo, Integer carNoType, Integer isStop, String description, String objectId) {
        SaveSepcialCar model = new SaveSepcialCar();
        model.setCarNo(carNo);
        model.setCarNoType(carNoType);
        model.setIsStop(isStop);
        model.setDescription(description);
        model.setObjectId(objectId);

        return post(model, "api/index/special/save", new ParameterizedTypeReference<DaoerBaseResp>() {
        });
    }

    /**
     * 查询特殊车辆
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<PageModel<SpecialMonthlyCarResult>>> getSpecialMonthlyCar(Integer pageNum, Integer pageSize) {
        SpecialCarSearch model = new SpecialCarSearch();
        model.configPage(pageNum, pageSize);

        return post(model, "api/index/special/getpage", new ParameterizedTypeReference<DaoerBaseResp<PageModel<SpecialMonthlyCarResult>>>() {
        });
    }


    /**
     * 月租车锁车/解锁
     *
     * @param carNo
     * @param status
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarLockResult>> lockMonthlyCar(String carNo, Integer status) {
        LockMonthlyCar model = new LockMonthlyCar();
        model.setCarNo(carNo);
        model.setStatus(status);
        return post(model, "api/index/lockCar/lockCar", new ParameterizedTypeReference<DaoerBaseResp<CarLockResult>>() {
        });
    }

    /**
     * 月租车锁车状态
     *
     * @param carNo
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarLockResult>> monthlyCarLockInfo(String carNo) {
        DaoerBase model = new DaoerBase();
        model.setCarNo(carNo);
        return post(model, "api/index/lockCar/getCarInfo", new ParameterizedTypeReference<DaoerBaseResp<CarLockResult>>() {
        });
    }

    /**
     * 补缴回调
     *
     * @param channelId
     * @param carNo
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> dueCarSuccess(String channelId, String carNo) {
        DueCarSuccess model = new DueCarSuccess();
        model.setCarNo(carNo);
        model.setDsn(channelId);

        return post(model, "api/index/arrears/payCallBack", DaoerBaseResp.class);
    }

    /**
     * 同步补缴配置信息
     *
     * @param notIn     是否不可进
     * @param notOut    是否不可出
     * @param startTime 生效开始时间
     * @param closeTime 生效结束时间
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> configDueCar(Integer notIn, Integer notOut, LocalTime startTime, LocalTime closeTime) {
        DueCarConfiguration model = new DueCarConfiguration();
        model.setUrgepayNotIn(notIn == 1 ? 0:1);
        model.setUrgepayNotOut(notOut == 1 ? 0:1);
        model.setStartTime(startTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
        model.setCloseTime(closeTime.format(DateTimeFormatter.ISO_LOCAL_TIME));

        return post(model, "api/index/arrears/configuration", DaoerBaseResp.class);
    }

    /**
     * 获取图片
     *
     * @param imgPath 图片路径
     * @return
     */
    @Override
    public Mono<byte[]> getImage(String imgPath) {
        Mono<byte[]> result = WebClient.create().get()
                .uri(imgUrl + imgPath)
                .retrieve()
                .bodyToMono(byte[].class)
                .doOnError(WebClientResponseException.class, err -> {
                    String errResult = err.getResponseBodyAsString();
                    log.error(errResult);
                    throw new RuntimeException(errResult);
                });

        return result;
    }

    /**
     * 生成URL
     *
     * @return
     */
    @Override
    public URLResult makeURL() {
        List<ChannelResult> channelResultList = getChannelsInfo().block().getBody();

        List<BlankCarURL> blankCarURLS = channelResultList.stream().filter(item -> item.getType() == 0).map(item -> {
            String stringBuilder = "?companyParkingLotCode=" +
                    parkId +
                    "&dsn=" +
                    item.getChannelId();

            BlankCarURL blankCarURL = new BlankCarURL();
            blankCarURL.setUrl(stringBuilder);
            blankCarURL.setChannelId(item.getChannelId());
            blankCarURL.setChannelName(item.getChannelName());
            blankCarURL.setType(item.getType());
            return blankCarURL;
        }).collect(Collectors.toList());

        List<CarOutPayURL> carOutPayURLS = channelResultList.stream().filter(item -> item.getType() == 1).map(item -> {
            String stringBuilder = "?companyParkingLotCode=" +
                    parkId +
                    "&dsn=" +
                    item.getChannelId();

            CarOutPayURL carOutPayURL = new CarOutPayURL();
            carOutPayURL.setUrl(stringBuilder);
            carOutPayURL.setChannelId(item.getChannelId());
            carOutPayURL.setChannelName(item.getChannelName());
            carOutPayURL.setType(item.getType());
            return carOutPayURL;
        }).collect(Collectors.toList());

        URLResult urlResult = new URLResult();
        urlResult.setBlankCarURLList(blankCarURLS);
        urlResult.setCarOutPayURLList(carOutPayURLS);

        return urlResult;
    }
}
