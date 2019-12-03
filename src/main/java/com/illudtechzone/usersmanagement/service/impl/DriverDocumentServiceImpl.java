package com.illudtechzone.usersmanagement.service.impl;

import com.illudtechzone.usersmanagement.service.DriverDocumentService;
import com.illudtechzone.usersmanagement.domain.DriverDocument;
import com.illudtechzone.usersmanagement.repository.DriverDocumentRepository;
import com.illudtechzone.usersmanagement.repository.search.DriverDocumentSearchRepository;
import com.illudtechzone.usersmanagement.service.dto.DriverDocumentDTO;
import com.illudtechzone.usersmanagement.service.mapper.DriverDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DriverDocument.
 */
@Service
@Transactional
public class DriverDocumentServiceImpl implements DriverDocumentService {

    private final Logger log = LoggerFactory.getLogger(DriverDocumentServiceImpl.class);

    private final DriverDocumentRepository driverDocumentRepository;

    private final DriverDocumentMapper driverDocumentMapper;

    private final DriverDocumentSearchRepository driverDocumentSearchRepository;

    public DriverDocumentServiceImpl(DriverDocumentRepository driverDocumentRepository, DriverDocumentMapper driverDocumentMapper, DriverDocumentSearchRepository driverDocumentSearchRepository) {
        this.driverDocumentRepository = driverDocumentRepository;
        this.driverDocumentMapper = driverDocumentMapper;
        this.driverDocumentSearchRepository = driverDocumentSearchRepository;
    }

    /**
     * Save a driverDocument.
     *
     * @param driverDocumentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DriverDocumentDTO save(DriverDocumentDTO driverDocumentDTO) {
        log.debug("Request to save DriverDocument : {}", driverDocumentDTO);
        DriverDocument driverDocument = driverDocumentMapper.toEntity(driverDocumentDTO);
        driverDocument = driverDocumentRepository.save(driverDocument);
        DriverDocumentDTO result = driverDocumentMapper.toDto(driverDocument);
        driverDocumentSearchRepository.save(driverDocument);
        return result;
    }

    /**
     * Get all the driverDocuments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DriverDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DriverDocuments");
        return driverDocumentRepository.findAll(pageable)
            .map(driverDocumentMapper::toDto);
    }


    /**
     * Get one driverDocument by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DriverDocumentDTO> findOne(Long id) {
        log.debug("Request to get DriverDocument : {}", id);
        return driverDocumentRepository.findById(id)
            .map(driverDocumentMapper::toDto);
    }

    /**
     * Delete the driverDocument by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DriverDocument : {}", id);
        driverDocumentRepository.deleteById(id);
        driverDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the driverDocument corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DriverDocumentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DriverDocuments for query {}", query);
        return driverDocumentSearchRepository.search(queryStringQuery(query), pageable)
            .map(driverDocumentMapper::toDto);
    }
}
