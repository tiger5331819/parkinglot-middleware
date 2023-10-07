package com.yfkyplatform.parkinglotmiddleware;

import com.yfkyplatform.passthrough.api.mgnt.PtParkingLotServiceApi;
import com.yfkyplatform.passthrough.api.mgnt.resp.GetPtParkingLotRpcResp;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ParkinglotServiceApplicationTests {

    @DubboReference(check = false)
    private PtParkingLotServiceApi ptParkingLotApi;

    @Test
    void sizeTest() {
        Integer testint = new Integer(19999);
        System.out.println(ObjectSizeCalculator.getObjectSize(testint));
        GetPtParkingLotRpcResp resp = new GetPtParkingLotRpcResp();
        System.out.println(ObjectSizeCalculator.getObjectSize(resp));
        System.out.println(ObjectSizeCalculator.getObjectSize(ptParkingLotApi));
        resp = ptParkingLotApi.getPtParkingLot(100102001120010000L);
        System.out.println(ObjectSizeCalculator.getObjectSize(resp));
        System.out.println(resp);
    }

    @Test
    void test(){
        BigDecimal money=new BigDecimal(6000);
        BigDecimal resultMoney=new BigDecimal(0);
        for(int i=1;i<=20;i++){
            resultMoney=resultMoney.add(money);
            resultMoney=resultMoney.add(resultMoney.multiply(new BigDecimal(0.04)));

        }
        resultMoney=resultMoney.add(new BigDecimal(1000).multiply(new BigDecimal(20)));
        System.out.println(resultMoney);
    }
}
