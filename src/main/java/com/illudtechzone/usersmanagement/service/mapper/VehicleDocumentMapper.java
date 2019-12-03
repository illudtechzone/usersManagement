package com.illudtechzone.usersmanagement.service.mapper;

import com.illudtechzone.usersmanagement.domain.*;
import com.illudtechzone.usersmanagement.service.dto.VehicleDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleDocument and its DTO VehicleDocumentDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class})
public interface VehicleDocumentMapper extends EntityMapper<VehicleDocumentDTO, VehicleDocument> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    VehicleDocumentDTO toDto(VehicleDocument vehicleDocument);

    @Mapping(source = "vehicleId", target = "vehicle")
    VehicleDocument toEntity(VehicleDocumentDTO vehicleDocumentDTO);

    default VehicleDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleDocument vehicleDocument = new VehicleDocument();
        vehicleDocument.setId(id);
        return vehicleDocument;
    }
}
