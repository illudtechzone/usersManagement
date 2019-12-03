package com.illudtechzone.usersmanagement.service;

import com.illudtechzone.usersmanagement.service.dto.VehicleDocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing VehicleDocument.
 */
public interface VehicleDocumentService {

    /**
     * Save a vehicleDocument.
     *
     * @param vehicleDocumentDTO the entity to save
     * @return the persisted entity
     */
    VehicleDocumentDTO save(VehicleDocumentDTO vehicleDocumentDTO);

    /**
     * Get all the vehicleDocuments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VehicleDocumentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" vehicleDocument.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VehicleDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" vehicleDocument.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the vehicleDocument corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VehicleDocumentDTO> search(String query, Pageable pageable);
}
