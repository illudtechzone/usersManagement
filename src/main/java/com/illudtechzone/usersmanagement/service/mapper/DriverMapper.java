package com.illudtechzone.usersmanagement.service.mapper;

import com.illudtechzone.usersmanagement.domain.*;
import com.illudtechzone.usersmanagement.service.dto.DriverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Driver and its DTO DriverDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {


    @Mapping(target = "documents", ignore = true)
    Driver toEntity(DriverDTO driverDTO);

    default Driver fromId(Long id) {
        if (id == null) {
            return null;
        }
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}
