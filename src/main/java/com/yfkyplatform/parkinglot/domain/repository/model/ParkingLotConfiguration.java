package com.yfkyplatform.parkinglot.domain.repository.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.reflect.ParameterizedType;

/**
 * 停车场配置项
 *
 * @author Suhuyuan
 */
@Entity
@Table(name="pl_cfg")
//@TypeDef(name="json",typeClass = JsonStringType.class)
@NoArgsConstructor
public class ParkingLotConfiguration<T> {
    @Id
    private String parkingLotId;

    //@JoinColumn(name = "config", columnDefinition = "json")
    //@Column(columnDefinition = "json")
    //@Type(type = "json")
    private String config;

    private String parkingType;

    public ParkingLotConfiguration(String parkingLotId, String parkingType) {
        this.parkingLotId = parkingLotId;
        this.parkingType = parkingType;
    }


    public T getConfig(Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        return mapper.readValue(config,clazz);
    }

    public void setConfig(T config) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        this.config = mapper.writeValueAsString(config);
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(String parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getParkingType() {
        return parkingType;
    }

    public void setParkingType(String parkingType) {
        this.parkingType = parkingType;
    }
}
