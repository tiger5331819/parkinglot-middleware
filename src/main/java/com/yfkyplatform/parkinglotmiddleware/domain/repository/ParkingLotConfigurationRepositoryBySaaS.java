package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import cn.hutool.core.util.StrUtil;
import com.yfkyframework.util.context.AccountRpcContext;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.DaoerConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.HongmenConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.yfkyplatform.passthrough.api.mgnt.resp.GetPtParkingLotRpcResp;
import com.yfkyplatform.passthrough.api.micro.PtParkingLotServiceMicroApi;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Primary
@Component
public class ParkingLotConfigurationRepositoryBySaaS implements IParkingLotConfigurationRepository {

    private final Environment env;
    @DubboReference(check = false)
    private PtParkingLotServiceMicroApi ptParkingLotServiceMicroApi;


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


    private ParkingLotConfiguration makeConfiguration(GetPtParkingLotRpcResp resp){
        String parkingType= ParkingLotManagerEnum.fromCode(resp.getMfrId()).getName();

        String prefix = "saasParkingLotConfig." + parkingType + ".";

        ParkingLotConfiguration cfg = new ParkingLotConfiguration();
        cfg.setId(String.valueOf(resp.getParkinglotId()));

        switch (parkingType){
            case "Daoer":{
                DaoerConfiguration daoerCfg = new DaoerConfiguration();
                daoerCfg.setAppName(resp.getThirdOSName());

                if(StrUtil.contains(resp.getThirdId(),"K")){
                    daoerCfg.setParkId(resp.getThirdId());
                    daoerCfg.setBaseUrl(env.getProperty(prefix + resp.getThirdId() + ".baseUrl"));
                    daoerCfg.setImgUrl(env.getProperty(prefix + resp.getThirdId() + ".imgUrl"));
                }else{
                    daoerCfg.setParkId(resp.getThirdId());
                    daoerCfg.setBaseUrl(env.getProperty(prefix + "baseUrl"));
                    daoerCfg.setImgUrl(env.getProperty(prefix + "imgUrl"));
                }

                daoerCfg.setBackTrack(resp.getBacktrack());
                cfg.setConfig(daoerCfg);
            }break;
            case "Hongmen":{
                HongmenConfiguration hongmenCfg=new HongmenConfiguration();
                hongmenCfg.setAppId(resp.getThirdId());
                hongmenCfg.setSecret(null);
                hongmenCfg.setBaseUrl(null);
                cfg.setConfig(hongmenCfg);
            }break;
            default:cfg.setConfig(new Object());break;
        }



        cfg.setManagerType(parkingType);
        cfg.setDescription(resp.getName());

        return cfg;
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
        Integer operatorId = AccountRpcContext.getOperatorId();
        log.info("查询SaaS车场信息，operatorId: " + operatorId);
        GetPtParkingLotRpcResp resp = ptParkingLotServiceMicroApi.getPtParkingLotMicroForOutside(Long.valueOf(parkingLotId), operatorId);

        return makeConfiguration(resp);
    }

    @Override
    public ParkingLotConfiguration findParkingLotConfigurationByThirdId(String thirdId,Integer operatorId) {
        GetPtParkingLotRpcResp resp = ptParkingLotServiceMicroApi.getParkingLotByThirdId(thirdId, operatorId);
        return makeConfiguration(resp);
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
