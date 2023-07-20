package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfig;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 停车场配置存储库实现
 *
 * @author Suhuyuan
 */
@Component
public class ParkingLotConfigurationRepositoryByRedis implements IParkingLotConfigurationRepository {

    private final RedisTool redis;

    private final String prefix = "cfg:";

    private final Environment env;

    private final ParkingLotConfig config;

    public ParkingLotConfigurationRepositoryByRedis(RedisTool redisTool, Environment env, ParkingLotConfig config) {
        redis = redisTool;
        this.env = env;
        this.config = config;
    }

    private List<ParkingLotConfiguration> makeConfigurationCache(String parkingType) {
        List<ParkingLotConfiguration> cache = new LinkedList<>();
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
                cache.add(cfg);
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
        if (redis.hash().values(redis.MakeKey(prefix + parkingType)).isEmpty()) {
            List<ParkingLotConfiguration> cache = makeConfigurationCache(parkingType);
            cache.forEach(item -> save(item));
        }
        return redis.hash().values(redis.MakeKey(prefix + parkingType));
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
        return (ParkingLotConfiguration) redis.hash().get(redis.MakeKey(prefix + parkingType), parkingLotId);
    }

    @Override
    public <S extends ParkingLotConfiguration> S save(S s) {
        redis.hash().put(redis.MakeKey(prefix + s.getParkingType()), s.getParkingLotId(), s);
        return s;
    }

    @Override
    public <S extends ParkingLotConfiguration> Iterable<S> saveAll(Iterable<S> iterable) {
        for (S data : iterable) {
            redis.hash().put(redis.MakeKey(prefix+data.getParkingType()),data.getParkingLotId(),data);
        }
        return iterable;
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
        redis.hash().delete(redis.MakeKey(prefix+parkingLotConfiguration.getParkingType()),parkingLotConfiguration.getParkingLotId());
    }

    @Override
    public void deleteAll(Iterable<? extends ParkingLotConfiguration> iterable) {
        for (ParkingLotConfiguration data:iterable) {
            redis.hash().delete(redis.MakeKey(prefix+data.getParkingType()),data.getParkingLotId());
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
