package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfig;
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

    private final ParkingLotConfig config;

    public ParkingLotConfigurationRepositoryByConfiguration(Environment environment, ParkingLotConfig config) {
        this.env = environment;
        this.config = config;
    }

    private Map<String, ParkingLotConfiguration> makeConfigurationCache(String parkingType) {
        Map<String, ParkingLotConfiguration> cache = new HashMap<>(100);
        if (config.getDaoer().isEmpty()) {
            return cache;
        }
        String saasParkingLotConfigPrefix = "saasParkingLotConfig." + parkingType + ".";

        if (parkingType.equals("Daoer")) {
            config.getDaoer().forEach(item -> {
                ParkingLotConfiguration cfg = new ParkingLotConfiguration();
                cfg.setParkingLotId(item.get("parkingLotId"));
                cfg.setParkingType("Daoer");
                cfg.setDescription(item.get("description"));

                DaoerConfiguration daoerCfg = new DaoerConfiguration();
                daoerCfg.setAppName(item.get("config." + "appName"));
                daoerCfg.setParkId(item.get("config." + "parkId"));
                daoerCfg.setBaseUrl(env.getProperty(saasParkingLotConfigPrefix + "baseUrl"));
                daoerCfg.setImgUrl(env.getProperty(saasParkingLotConfigPrefix + "imgUrl"));
                daoerCfg.setBackTrack(Boolean.valueOf(item.get("config." + "backTrack")));

                cfg.setConfig(daoerCfg);
                cache.put(cfg.getParkingLotId(), cfg);
            });
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
    public ParkingLotConfiguration findParkingLotConfigurationByParkingTypeAndParkingLotId(String parkingType, String parkingLotId) {
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
