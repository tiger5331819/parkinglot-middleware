package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderWithArrearResultByList;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 车辆基础信息
 *
 * @author Suhuyuan
 */
@Data
public class Car {

    /**
     * 车牌号
     */
    private String carNo;

    /**
     * 车辆月租类型
     */
    private int typeId = -1;

    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeStartTime;

    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeEndTime;

    /**
     * 0正常,2即将过期，6过期, 7待审核，8待支付 -1注销,
     */
    private int typeStatus;

    /**
     * 车主姓名
     */
    private String contactName = "临时用户";

    /**
     * 车主电话号码
     */
    private String contactPhone;

    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime inTime;

    /**
     * 入场照片
     */
    private String inPic;

    /**
     * 车场唯一入场记录ID
     */
    private String inId;

    /**
     * 当前订单信息
     */
    private PayMessage order;

    /**
     * 历史欠费订单信息
     */
    private List<PayMessage> arrearOrder;

    /**
     * 车辆所处位置
     */
    private Space carSpace;

    /**
     * 领域规则：通过查询第三方系统的车辆费用生成订单信息
     *
     * @param carOrderResult 第三方系统的车辆费用
     */
    public void makeOrder(CarOrderResult carOrderResult) {
        PayMessage payMessage = new PayMessage();
        payMessage.setCreateTime(carOrderResult.getCreateTime());
        payMessage.setInTime(carOrderResult.getStartTime());
        payMessage.setTotalFee(carOrderResult.getTotalFee());
        payMessage.setPayFee(carOrderResult.getPayFee());
        payMessage.setDiscountFee(carOrderResult.getDiscountFee());

        this.setOrder(payMessage);

        if (carOrderResult instanceof CarOrderWithArrearResultByList) {
            CarOrderWithArrearResultByList arrearResultByList = (CarOrderWithArrearResultByList) carOrderResult;
            payMessage.setInId(arrearResultByList.getInId());
            payMessage.setOverTime(arrearResultByList.getOverTime());

            if (AssertTool.checkCollectionNotNull(arrearResultByList.getArrearList())) {
                this.setArrearOrder(arrearResultByList.getArrearList().stream().map(arrear -> {
                    PayMessage arrearPayMessage = new PayMessage();
                    arrearPayMessage.setCreateTime(arrear.getCreateTime());
                    arrearPayMessage.setInTime(arrear.getStartTime());
                    arrearPayMessage.setTotalFee(arrear.getTotalFee());
                    arrearPayMessage.setPayFee(arrear.getPayFee());
                    arrearPayMessage.setDiscountFee(arrear.getDiscountFee());
                    arrearPayMessage.setInId(arrear.getInId());
                    arrearPayMessage.setOverTime(arrear.getOverTime());
                    return arrearPayMessage;
                }).collect(Collectors.toList()));
            }
        }
    }

    /**
     * 领域规则：通过入场唯一ID定位订单
     *
     * @param inId 入场唯一ID
     * @return
     */
    public PayMessage findOrder(String inId) {
        if (ObjectUtil.isNotNull(this.getOrder())) {
            if (StrUtil.equals(this.getOrder().getInId(), inId)) {
                return this.getOrder();
            }
        }
        if (AssertTool.checkCollectionNotNull(this.getArrearOrder())) {
            List<PayMessage> orderList = this.getArrearOrder();
            Optional<PayMessage> orderOptional = orderList.stream().filter(item -> StrUtil.equals(item.getInId(), inId)).findFirst();
            if (orderOptional.isPresent()) {
                return orderOptional.get();
            }
        }
        return null;
    }
}
