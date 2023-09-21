package com.yfkyplatform.parkinglotmiddleware.domain.repository;

import com.yfkyplatform.parkinglotmiddleware.domain.repository.model.ParkingLotConfiguration;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 停车场配置存储库
 *
 * @author Suhuyuan
 */

public interface IParkingLotConfigurationRepository extends CrudRepository<ParkingLotConfiguration, String> {

    /**
     * 通过停车场类型来获取停车场配置
     *
     * @param parkingType 停车场类型
     * @return
     */
    List<ParkingLotConfiguration> findParkingLotConfigurationByParkingType(String parkingType);

    /**
     * 通过停车场类型与停车场Id获取停车场配置
     *
     * @param parkingType  停车场类型
     * @param parkingLotId 停车场Id
     * @return
     */
    ParkingLotConfiguration findParkingLotConfigurationByParkingTypeAndParkingLotId(String parkingType, String parkingLotId);

    /**
     * 通过第三方停车场Id获取停车场配置
     *
     * @param thirdId
     * @param operatorId
     * @return
     */
    ParkingLotConfiguration findParkingLotConfigurationByThirdId(String thirdId,Integer operatorId);
}
