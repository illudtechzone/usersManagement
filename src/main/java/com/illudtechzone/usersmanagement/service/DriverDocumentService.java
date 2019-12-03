package com.illudtechzone.usersmanagement.service;

import com.illudtechzone.usersmanagement.service.dto.DriverDocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DriverDocument.
 */
public interface DriverDocumentService {

    /**
     * Save a driverDocument.
     *
     * @param driverDocumentDTO the entity to save
     * @return the persisted entity
     */
    DriverDocumentDTO save(DriverDocumentDTO driverDocumentDTO);

    /**
     * Get all the driverDocuments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DriverDocumentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" driverDocument.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DriverDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" driverDocument.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the driverDocument corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DriverDocumentDTO> search(String query, Pageable pageable);
}
