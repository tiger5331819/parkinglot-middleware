package com.yfkyplatform.parkinglot.domain.repository;

import com.yfkyplatform.parkinglot.domain.repository.model.ParkingLotConfiguration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 停车场配置存储库
 *
 * @author Suhuyuan
 */

public interface ParkingLotConfigurationRepository extends CrudRepository<ParkingLotConfiguration,String> {
    /**
     * 通过Id 获取停车场配置
     * @param Id
     * @return
     */
    ParkingLotConfiguration findParkingLotConfigurationByParkingLotId(String Id);

    /**
     * 通过停车场类型来获取停车场配置
     * @param parkingType
     * @return
     */
    List<ParkingLotConfiguration> findParkingLotConfigurationByParkingType(String parkingType);
}
