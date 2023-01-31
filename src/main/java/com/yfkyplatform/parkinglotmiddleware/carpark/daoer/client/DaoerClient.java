package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.DaoerWebClient;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon.CouponQuery;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon.CouponUse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.guest.GuestMessage;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.guest.GuestWithId;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar.CreateMonthlyCar;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar.RenewalMonthlyCar;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.guest.GuestResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 道尔云API代理
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerClient extends DaoerWebClient implements IDaoerCarPort, IDaoerMonthlyCar, IDaoerGuest, IDaoerCoupon, IDaoerTool {

    public DaoerClient(String id,String appName, String parkId, String baseUrl, RedisTool redisTool){
        super(id,appName, parkId, baseUrl, redisTool);
    }

    /**
     * 获取车位使用情况
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo(){
        DaoerBase model=new DaoerBase("api/index/carport");
        return post(model, new ParameterizedTypeReference<DaoerBaseResp<List<CarportResult>>>() {});
    }

    /**
     * 获取临时车缴纳金额
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo){
        DaoerBase model=new DaoerBase("api/index/tempFee/getcarfee");
        model.setCarNo(carNo);
        return post(model, new ParameterizedTypeReference<DaoerBaseResp<CarFeeResult>>() {});
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
     * 根据通道号获取车辆费用信息
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarFeeResult>> getChannelCarFee(String channelId,String carNo, String openId){
        ChannelCarFee model=new ChannelCarFee("/api/index/tempFee/getCarBayDsn");

        model.setCarNo(carNo);
        model.setDsn(channelId);
        model.setOpenId(openId);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<CarFeeResult>>() {});
    }

    /**
     * 无牌车入场
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<BlankCarInResult>> blankCarIn(String openId, int scanType, String channelId){
        BlankCarInOrOut model=new BlankCarInOrOut("api/index/scanin");

        model.setChannelId(channelId);
        model.setScanType(scanType);
        model.setOpenId(openId);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<BlankCarInResult>>() {});
    }

    /**
     * 无牌车出场
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<BlankCarOutResult>> blankCarOut(String openId, int scanType, String channelId){
        BlankCarInOrOut model=new BlankCarInOrOut("api/index/scanout");

        model.setChannelId(channelId);
        model.setScanType(scanType);
        model.setOpenId(openId);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<BlankCarOutResult>>() {});
    }

    /**
     * 获取入场记录
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarInOrOutResult<CarInData>>> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize){
        CarInOrOut model=new CarInOrOut("api/index/carins");

        model.setCarNo(carNo);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.configPage(pageNum,pageSize);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<CarInOrOutResult<CarInData>>>() {});
    }

    /**
     * 获取出场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CarInOrOutResult<CarOutData>>> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize){
        CarInOrOut model=new CarInOrOut("api/index/carouts");

        model.setCarNo(carNo);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.configPage(pageNum,pageSize);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<CarInOrOutResult<CarOutData>>>() {});
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelResult>>> getChannelsInfo(){
        DaoerBase model=new DaoerBase("api/index/getchannels");
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<List<ChannelResult>>>() {});
    }

    /**
     * 获取设备状态
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelStateResult>>> getChannelStates(){
        DaoerBase model=new DaoerBase("api/index/getchannelstatus");
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<List<ChannelStateResult>>>() {});
    }

    /**
     * 控制道闸开、关。
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<ChannelStatusResult>>> controlChannel(String channelId, int channelIdStatus){
        Channel model=new Channel("api/index/onandoff");
        model.setChannelId(channelId);
        model.setChannelIdStatus(channelIdStatus);
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<List<ChannelStatusResult>>>() {});
    }

    /**
     * 创建访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<GuestResult>> createGuest(String guestName, String carNo, LocalDateTime visitTime, String mobile, String description){
        GuestMessage model=new GuestMessage("api/guest/create");
        model.setGuestName(guestName);
        model.setDescription(description);
        model.setMobile(mobile);
        model.setVisitTime(visitTime);
        model.setCarNo(carNo);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<GuestResult>>() {});
    }

    /**
     * 修改访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> changeGuestMessage(String objectId,String guestName,LocalDateTime visitTime,String mobile,String description){
        GuestMessage model=new GuestWithId("api/guest/update",objectId);
        model.setGuestName(guestName);
        model.setDescription(description);
        model.setMobile(mobile);
        model.setVisitTime(visitTime);

        return post(model,DaoerBaseResp.class);
    }

    /**
     * 删除访客
     * @return
     */
    @Override
    public Mono<DaoerBaseResp> removeGuestMessage(String objectId){
        GuestMessage model=new GuestWithId("api/guest/cancel",objectId);

        return post(model,DaoerBaseResp.class);
    }

    /**
     * 查询优惠券
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<CouponResult>>> getCoupon(String openId){
        CouponQuery model=new CouponQuery("api/index/coupon/getcoupon");
        model.setOpenId(openId);
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<List<CouponResult>>>() {});
    }

    /**
     * 使用优惠券
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<CouponUseResult>> useCoupon(String objectId, String carNo){
        CouponUse model=new CouponUse("api/index/coupon/bindcar");
        model.setObjectId(objectId);
        model.setCarNo(carNo);
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<CouponUseResult>>() {});
    }

    /**
     * 获取停车场下的月卡费率
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>> getMonthlyCarLongRentalRate(){
        DaoerBase model=new DaoerBase("api/index/monthlycar/long-rental-rate");
        model.appendUri("/" + parkId);

        return get(model,new ParameterizedTypeReference<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>>() {});
    }

    /**
     * 获取月租车基本信息
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> getMonthlyCarInfo(String carNo) {
        DaoerBase model = new DaoerBase("api/index/monthlycar/info");
        model.setCarNo(carNo);

        return post(model, new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {
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
                                                                  String concatName, String concatPhone) {
        CreateMonthlyCar model = new CreateMonthlyCar("api/index/monthlycar/create");
        model.setBalanceMoney(balanceMoney);
        model.setCardTypeId(cardType);
        model.setStartTime(startTime);
        model.setEndTime(endTime);
        model.setPayType(payType);
        model.setConcatName(concatName);
        model.setConcatPhone(concatPhone);
        model.setCarNo(carNo);

        return post(model, new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {
        });
    }

    /**
     * 获取月租车缴费历史
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<List<MonthlyCarHistoryResult>>> getMonthlyCarHistory(String carNo) {
        DaoerBase model = new DaoerBase("api/index/monthlycar/history");
        model.setCarNo(carNo);

        return post(model,new ParameterizedTypeReference<DaoerBaseResp<List<MonthlyCarHistoryResult>>>() {});
    }

    /**
     * 月租车续期
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> renewalMonthlyCar(String carNo,String newStartTime,String newEndTime,String balanceMoney,int payType){
        RenewalMonthlyCar model= new RenewalMonthlyCar("api/index/monthlycar/newdate");
        model.setCarNo(carNo);
        model.setNewEndTime(newEndTime);
        model.setNewStartTime(newStartTime);
        model.setBalanceMoney(balanceMoney);
        model.setPayType(payType);
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {});
    }

    /**
     * 月租车销户
     *
     * @return
     */
    @Override
    public Mono<DaoerBaseResp<MonthlyCarResult>> removeMonthlyCar(String carNo){
        DaoerBase model= new DaoerBase("api/index/monthlycar/del");
        model.setCarNo(carNo);
        return post(model,new ParameterizedTypeReference<DaoerBaseResp<MonthlyCarResult>>() {});
    }

    /**
     * 获取图片
     * @param imgPath 图片路径
     * @return
     */
    @Override
    public Mono<byte[]> getImage(String imgPath){
        DaoerBase model=new DaoerBase("img");
        model.appendUri(imgPath);
        return get(model,byte[].class);
    }
}
