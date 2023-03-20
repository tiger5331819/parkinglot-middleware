package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.bean.BeanUtil;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.DaoerParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class BeanUtilCopyTest {
    @Autowired
    private ParkingLotManagerFactory factory;



    @Test
    void beanCopyTest(){
        List<ParkingLotConfiguration> cfgList=factory.getParkingLotConfiguration(null,null);
        List<ParkingLotCfgRpcResp> data=new ArrayList<>();
        cfgList.forEach(item->{
            if((item instanceof DaoerParkingLotConfiguration)){
                data.add(BeanUtil.copyProperties(item,DaoerParkingLotCfgRpcResp.class));
            }
        });

        assertNotNull(data);
        assertNotEquals(0,data.size());
        data.forEach(item->{
            System.out.println(item);
        });
    }
}
