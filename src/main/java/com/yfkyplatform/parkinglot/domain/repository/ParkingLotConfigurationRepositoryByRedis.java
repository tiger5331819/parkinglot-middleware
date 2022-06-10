package com.yfkyplatform.parkinglot.domain.repository;

import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.repository.model.ParkingLotConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 停车场配置存储库实现
 *
 * @author Suhuyuan
 */
@Component
public class ParkingLotConfigurationRepositoryByRedis implements IParkingLotConfigurationRepository {

    private RedisTool redis;

    private String prefix="cfg:";

    public ParkingLotConfigurationRepositoryByRedis(RedisTool redisTool){
        redis=redisTool;
    }

    /**
     * 通过停车场类型来获取停车场配置
     *
     * @param parkingType
     * @return
     */
    @Override
    public List<ParkingLotConfiguration> findParkingLotConfigurationByParkingType(String parkingType) {
        return redis.hash().values(redis.MakeKey(prefix+parkingType));
    }

    @Override
    public <S extends ParkingLotConfiguration> S save(S s) {
        redis.hash().put(redis.MakeKey(prefix+s.getParkingType()),s.getParkingLotId(),s);
        return s;
    }

    @Override
    public <S extends ParkingLotConfiguration> Iterable<S> saveAll(Iterable<S> iterable) {
        for (S data:iterable) {
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
