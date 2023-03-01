package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.LifangConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 停车场配置存储库实现
 *
 * @author Suhuyuan
 */
@Slf4j
@Component
public class ParkingLotConfigurationRepositoryByConfiguration implements IParkingLotConfigurationRepository {

    private final Environment env;

    public ParkingLotConfigurationRepositoryByConfiguration(Environment environment) {
        this.env = environment;
    }

    private Map<String, ParkingLotConfiguration> makeConfigurationCache(String parkingType) {
        Map<String, ParkingLotConfiguration> cache = new HashMap<>(100);
        String prefix = "parkingLotConfig." + parkingType + ".";
        String list = env.getProperty(prefix + "all");
        if (StrUtil.isBlank(list)) {
            return cache;
        }
        String[] cfgList = list.split(",");
        for (String cfgId : cfgList) {
            String prefix2 = "parkingLotConfig." + parkingType + "." + cfgId + ".";

            ParkingLotConfiguration cfg = new ParkingLotConfiguration();
            cfg.setParkingLotId(env.getProperty(prefix2 + "parkingLotId"));

            switch (parkingType) {
                case "Daoer": {
                    DaoerConfiguration daoerCfg = new DaoerConfiguration();
                    daoerCfg.setAppName(env.getProperty(prefix2 + "config." + "appName"));
                    daoerCfg.setParkId(env.getProperty(prefix2 + "config." + "parkId"));
                    daoerCfg.setBaseUrl(env.getProperty(prefix2 + "config." + "baseUrl"));
                    daoerCfg.setImgUrl(env.getProperty(prefix2 + "config." + "imgUrl"));
                    cfg.setConfig(daoerCfg);
                }
                break;
                case "Lifang": {
                    LifangConfiguration lifangCfg = new LifangConfiguration();
                    lifangCfg.setSecret(env.getProperty(prefix2 + "config." + "secret"));
                    lifangCfg.setBaseUrl(env.getProperty(prefix2 + "config." + "baseUrl"));
                    cfg.setConfig(lifangCfg);
                }
                break;
                default:
                    cfg.setConfig(new Object());
                    break;
            }

            cfg.setParkingType(parkingType);
            cfg.setDescription(env.getProperty(prefix2 + "description"));

            cache.put(cfg.getParkingLotId(), cfg);
        }
        return cache;
    }

    /**
     * 通过停车场类型来获取停车场配置
     *
     * @param parkingType
     * @return
     */
    @Override
    public List<ParkingLotConfiguration> findParkingLotConfigurationByParkingType(String parkingType) {
        return new ArrayList<>(makeConfigurationCache(parkingType).values());
    }

    /**
     * 通过停车场类型与停车场Id获取停车场配置
     *
     * @param parkingType  停车场类型
     * @param parkingLotId 停车场Id
     * @return
     */
    @Override
    public ParkingLotConfiguration findParkingLotConfigurationByParkingTypeAndAndParkingLotId(String parkingType, String parkingLotId) {
        Map<String, ParkingLotConfiguration> cache = makeConfigurationCache(parkingType);
        return cache.get(parkingLotId);
    }

    @Override
    public <S extends ParkingLotConfiguration> S save(S s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends ParkingLotConfiguration> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ParkingLotConfiguration> findById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<ParkingLotConfiguration> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<ParkingLotConfiguration> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(ParkingLotConfiguration parkingLotConfiguration) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends ParkingLotConfiguration> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
