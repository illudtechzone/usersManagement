package com.illudtechzone.usersmanagement.service.impl;

import com.illudtechzone.usersmanagement.service.VehicleDocumentService;
import com.illudtechzone.usersmanagement.domain.VehicleDocument;
import com.illudtechzone.usersmanagement.repository.VehicleDocumentRepository;
import com.illudtechzone.usersmanagement.repository.search.VehicleDocumentSearchRepository;
import com.illudtechzone.usersmanagement.service.dto.VehicleDocumentDTO;
import com.illudtechzone.usersmanagement.service.mapper.VehicleDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VehicleDocument.
 */
@Service
@Transactional
public class VehicleDocumentServiceImpl implements VehicleDocumentService {

    private final Logger log = LoggerFactory.getLogger(VehicleDocumentServiceImpl.class);

    private final VehicleDocumentRepository vehicleDocumentRepository;

    private final VehicleDocumentMapper vehicleDocumentMapper;

    private final VehicleDocumentSearchRepository vehicleDocumentSearchRepository;

    public VehicleDocumentServiceImpl(VehicleDocumentRepository vehicleDocumentRepository, VehicleDocumentMapper vehicleDocumentMapper, VehicleDocumentSearchRepository vehicleDocumentSearchRepository) {
        this.vehicleDocumentRepository = vehicleDocumentRepository;
        this.vehicleDocumentMapper = vehicleDocumentMapper;
        this.vehicleDocumentSearchRepository = vehicleDocumentSearchRepository;
    }

    /**
     * Save a vehicleDocument.
     *
     * @param vehicleDocumentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VehicleDocumentDTO save(VehicleDocumentDTO vehicleDocumentDTO) {
        log.debug("Request to save VehicleDocument : {}", vehicleDocumentDTO);
        VehicleDocument vehicleDocument = vehicleDocumentMapper.toEntity(vehicleDocumentDTO);
        vehicleDocument = vehicleDocumentRepository.save(vehicleDocument);
        VehicleDocumentDTO result = vehicleDocumentMapper.toDto(vehicleDocument);
        vehicleDocumentSearchRepository.save(vehicleDocument);
        return result;
    }

    /**
     * Get all the vehicleDocuments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleDocuments");
        return vehicleDocumentRepository.findAll(pageable)
            .map(vehicleDocumentMapper::toDto);
    }


    /**
     * Get one vehicleDocument by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleDocumentDTO> findOne(Long id) {
        log.debug("Request to get VehicleDocument : {}", id);
        return vehicleDocumentRepository.findById(id)
            .map(vehicleDocumentMapper::toDto);
    }

    /**
     * Delete the vehicleDocument by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VehicleDocument : {}", id);
        vehicleDocumentRepository.deleteById(id);
        vehicleDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the vehicleDocument corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VehicleDocumentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VehicleDocuments for query {}", query);
        return vehicleDocumentSearchRepository.search(queryStringQuery(query), pageable)
            .map(vehicleDocumentMapper::toDto);
    }
}
