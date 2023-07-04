package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import com.yfkyplatform.passthrough.api.mgnt.PtParkingLotServiceApi;
import com.yfkyplatform.passthrough.api.mgnt.resp.GetPtParkingLotRpcResp;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 停车场配置存储库实现
 *
 * @author Suhuyuan
 */
@Primary
@Component
public class ParkingLotConfigurationRepositoryBySaaS implements IParkingLotConfigurationRepository {

    private final Environment env;
    @DubboReference(check = false)
    private PtParkingLotServiceApi ptParkingLotApi;

    public ParkingLotConfigurationRepositoryBySaaS(Environment environment) {
        this.env = environment;
    }

    /**
     * 通过停车场类型来获取停车场配置
     *
     * @param parkingType
     * @return
     */
    @Override
    public List<ParkingLotConfiguration> findParkingLotConfigurationByParkingType(String parkingType) {
        throw new UnsupportedOperationException();
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
        GetPtParkingLotRpcResp resp = ptParkingLotApi.getPtParkingLot(Long.valueOf(parkingLotId));

        String prefix = "saasParkingLotConfig." + parkingType + ".";

        ParkingLotConfiguration cfg = new ParkingLotConfiguration();
        cfg.setParkingLotId(parkingLotId);

        if (parkingType.equals("Daoer")) {
            DaoerConfiguration daoerCfg = new DaoerConfiguration();
            daoerCfg.setAppName(resp.getThirdOSName());
            daoerCfg.setParkId(resp.getThirdId());
            daoerCfg.setBaseUrl(env.getProperty(prefix + "baseUrl"));
            daoerCfg.setImgUrl(env.getProperty(prefix + "imgUrl"));
            //daoerCfg.setBackTrack(resp.get);
            cfg.setConfig(daoerCfg);
        } else {
            cfg.setConfig(new Object());
        }

        cfg.setParkingType(parkingType);
        cfg.setDescription(resp.getName());

        return cfg;
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
