package com.illudtechzone.usersmanagement.service.mapper;

import com.illudtechzone.usersmanagement.domain.*;
import com.illudtechzone.usersmanagement.service.dto.VehicleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {DriverMapper.class})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {

    @Mapping(source = "drivers.id", target = "driversId")
    VehicleDTO toDto(Vehicle vehicle);

    @Mapping(target = "documents", ignore = true)
    @Mapping(source = "driversId", target = "drivers")
    Vehicle toEntity(VehicleDTO vehicleDTO);

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
