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
    <T>ParkingLotConfiguration<T> findParkingLotConfigurationByParkingLotId(String Id);
    <T>List<ParkingLotConfiguration<T>> findParkingLotConfigurationByParkingType(String Id);
}
