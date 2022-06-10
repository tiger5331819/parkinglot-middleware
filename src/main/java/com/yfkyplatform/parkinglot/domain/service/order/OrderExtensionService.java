package com.yfkyplatform.parkinglot.domain.service.order;

import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManagerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * 订单服务
 *
 * @author Suhuyuan
 */
@Component
public class OrderExtensionService {

    private ParkingLotManagerFactory factory;

    public OrderExtensionService(ParkingLotManagerFactory factory){
        this.factory=factory;
    }

    /**
     * 生成车辆入场通知
     * @param parkingLotManagerName
     * @param parkingNo
     * @param carNo
     * @param carType
     * @param inTime
     * @param inPic
     * @return
     */
    public CarInNotice makeCarInNotice(String parkingLotManagerName, String parkingNo, String carNo,
                                     int carType, LocalDateTime inTime,String inPic){
        CarInNotice carInNotice =new CarInNotice();

        try{
            carInNotice.setParkingLotId(factory.manager(parkingLotManagerName).parkingLotFromPark(parkingNo).Id());
        }catch (NoSuchElementException ex){
            throw new RuntimeException(ex);
        }

        carInNotice.setCarNo(carNo);
        carInNotice.setCarType(carType);
        carInNotice.setInPic(inPic);
        carInNotice.setInTime(inTime);

        return carInNotice;
    }

    /**
     * 生成车辆出场通知
     * @param parkingLotManagerName
     * @param parkingNo
     * @param carNo
     * @param carType
     * @param outTime
     * @param outPic
     * @return
     */
    public CarOutNotice makeCarOutNotice(String parkingLotManagerName, String parkingNo, String carNo,
                                      int carType, LocalDateTime outTime,String outPic){
        CarOutNotice caroutNotice =new CarOutNotice();

        try{
            caroutNotice.setParkingLotId(factory.manager(parkingLotManagerName).parkingLotFromPark(parkingNo).Id());
        }catch (NoSuchElementException ex){
            throw new RuntimeException(ex);
        }

        caroutNotice.setCarNo(carNo);
        caroutNotice.setCarType(carType);
        caroutNotice.setOutPic(outPic);
        caroutNotice.setOutTime(outTime);

        return caroutNotice;
    }
}
