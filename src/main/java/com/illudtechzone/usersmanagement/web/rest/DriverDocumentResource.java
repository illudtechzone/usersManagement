package com.illudtechzone.usersmanagement.web.rest;
import com.illudtechzone.usersmanagement.service.DriverDocumentService;
import com.illudtechzone.usersmanagement.web.rest.errors.BadRequestAlertException;
import com.illudtechzone.usersmanagement.web.rest.util.HeaderUtil;
import com.illudtechzone.usersmanagement.web.rest.util.PaginationUtil;
import com.illudtechzone.usersmanagement.service.dto.DriverDocumentDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DriverDocument.
 */
@RestController
@RequestMapping("/api")
public class DriverDocumentResource {

    private final Logger log = LoggerFactory.getLogger(DriverDocumentResource.class);

    private static final String ENTITY_NAME = "usersManagementDriverDocument";

    private final DriverDocumentService driverDocumentService;

    public DriverDocumentResource(DriverDocumentService driverDocumentService) {
        this.driverDocumentService = driverDocumentService;
    }

    /**
     * POST  /driver-documents : Create a new driverDocument.
     *
     * @param driverDocumentDTO the driverDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new driverDocumentDTO, or with status 400 (Bad Request) if the driverDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/driver-documents")
    public ResponseEntity<DriverDocumentDTO> createDriverDocument(@RequestBody DriverDocumentDTO driverDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save DriverDocument : {}", driverDocumentDTO);
        if (driverDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new driverDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DriverDocumentDTO result = driverDocumentService.save(driverDocumentDTO);
        return ResponseEntity.created(new URI("/api/driver-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /driver-documents : Updates an existing driverDocument.
     *
     * @param driverDocumentDTO the driverDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated driverDocumentDTO,
     * or with status 400 (Bad Request) if the driverDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the driverDocumentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/driver-documents")
    public ResponseEntity<DriverDocumentDTO> updateDriverDocument(@RequestBody DriverDocumentDTO driverDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update DriverDocument : {}", driverDocumentDTO);
        if (driverDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DriverDocumentDTO result = driverDocumentService.save(driverDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, driverDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /driver-documents : get all the driverDocuments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of driverDocuments in body
     */
    @GetMapping("/driver-documents")
    public ResponseEntity<List<DriverDocumentDTO>> getAllDriverDocuments(Pageable pageable) {
        log.debug("REST request to get a page of DriverDocuments");
        Page<DriverDocumentDTO> page = driverDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/driver-documents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /driver-documents/:id : get the "id" driverDocument.
     *
     * @param id the id of the driverDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the driverDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/driver-documents/{id}")
    public ResponseEntity<DriverDocumentDTO> getDriverDocument(@PathVariable Long id) {
        log.debug("REST request to get DriverDocument : {}", id);
        Optional<DriverDocumentDTO> driverDocumentDTO = driverDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(driverDocumentDTO);
    }

    /**
     * DELETE  /driver-documents/:id : delete the "id" driverDocument.
     *
     * @param id the id of the driverDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/driver-documents/{id}")
    public ResponseEntity<Void> deleteDriverDocument(@PathVariable Long id) {
        log.debug("REST request to delete DriverDocument : {}", id);
        driverDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/driver-documents?query=:query : search for the driverDocument corresponding
     * to the query.
     *
     * @param query the query of the driverDocument search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/driver-documents")
    public ResponseEntity<List<DriverDocumentDTO>> searchDriverDocuments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DriverDocuments for query {}", query);
        Page<DriverDocumentDTO> page = driverDocumentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/driver-documents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
