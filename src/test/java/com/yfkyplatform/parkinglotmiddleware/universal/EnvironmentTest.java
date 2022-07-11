package com.yfkyplatform.parkinglotmiddleware.universal;

import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class EnvironmentTest {
    @Autowired
    private Environment env;

    @Test
    void environmentTest() {
        String parkingType = "Daoer";
        String prefix = "parkingLotConfig." + parkingType + ".";
        String list = env.getProperty(prefix + "all");
        String[] cfgList = list.split(",");
        for (String cfgId : cfgList) {
            String prefix2 = "parkingLotConfig." + parkingType + "." + cfgId + ".";

            ParkingLotConfiguration cfg = new ParkingLotConfiguration();
            cfg.setParkingLotId(prefix2 + "parkingLotId");

            switch (parkingType) {
                case "Daoer": {
                    DaoerConfiguration daoerCfg = new DaoerConfiguration();
                    daoerCfg.setAppName(env.getProperty(prefix2 + "config." + "appName"));
                    daoerCfg.setParkId(env.getProperty(prefix2 + "config." + "parkId"));
                    daoerCfg.setBaseUrl(env.getProperty(prefix2 + "config." + "baseUrl"));
                    cfg.setConfig(daoerCfg);
                }
                break;
                default:
                    cfg.setConfig(new Object());
                    break;
            }

            cfg.setParkingType(parkingType);
            cfg.setDescription(env.getProperty(prefix2 + "description"));
        }
        System.out.println();
    }

}
