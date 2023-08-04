package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 停车场配置存储库实现
 *
 * @author Suhuyuan
 */
@Component
public class ParkingLotConfigurationRepositoryByRedis implements IParkingLotConfigurationRepository {

    private final RedisTool redis;

    private final String prefix = "cfg:";

    private final TestBox testBox;

    public ParkingLotConfigurationRepositoryByRedis(RedisTool redisTool, TestBox testBox) {
        redis = redisTool;
        this.testBox = testBox;
    }

    private List<ParkingLotConfiguration<?>> makeConfigurationCache(String parkingType) {
        List<ParkingLotConfiguration<?>> cache = new LinkedList<>();
        if (parkingType.equals("Daoer")) {
            String data = "{\"pageNum\":1,\"pageSize\":1000000}";
            Map<String, Object> result = null;
            try {
                result = testBox.drCloudClient().post(data, "api/backstage/regist/findall");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Map<String, Object> resultData = (Map<String, Object>) result.get("data");
            List<Map<String, Object>> list = (List<Map<String, Object>>) resultData.get("list");
            cache = list.stream().map(item -> {
                String saasParkingLotConfigPrefix = "saasParkingLotConfig.Daoer.";
                ParkingLotConfiguration<DaoerConfiguration> cfg = new ParkingLotConfiguration<>();
                cfg.setParkingLotId((String) item.get("parkNo"));
                cfg.setParkingType("Daoer");
                cfg.setDescription((String) item.get("parkName"));

                DaoerConfiguration daoerCfg = new DaoerConfiguration();
                daoerCfg.setAppName((String) item.get("appName"));
                daoerCfg.setParkId((String) item.get("parkNo"));
                daoerCfg.setBaseUrl(testBox.env.getProperty(saasParkingLotConfigPrefix + "baseUrl"));
                daoerCfg.setImgUrl(testBox.env.getProperty(saasParkingLotConfigPrefix + "imgUrl"));
                daoerCfg.setBackTrack(Boolean.valueOf(testBox.env.getProperty(saasParkingLotConfigPrefix + "backTrack")));

                cfg.setConfig(daoerCfg);

                return cfg;
            }).collect(Collectors.toList());
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
            List<ParkingLotConfiguration<?>> cache = makeConfigurationCache(parkingType);
            saveAll(cache);
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
