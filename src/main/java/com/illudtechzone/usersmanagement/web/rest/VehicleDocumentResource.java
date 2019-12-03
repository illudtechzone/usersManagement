package com.illudtechzone.usersmanagement.web.rest;
import com.illudtechzone.usersmanagement.service.VehicleDocumentService;
import com.illudtechzone.usersmanagement.web.rest.errors.BadRequestAlertException;
import com.illudtechzone.usersmanagement.web.rest.util.HeaderUtil;
import com.illudtechzone.usersmanagement.web.rest.util.PaginationUtil;
import com.illudtechzone.usersmanagement.service.dto.VehicleDocumentDTO;
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
 * REST controller for managing VehicleDocument.
 */
@RestController
@RequestMapping("/api")
public class VehicleDocumentResource {

    private final Logger log = LoggerFactory.getLogger(VehicleDocumentResource.class);

    private static final String ENTITY_NAME = "usersManagementVehicleDocument";

    private final VehicleDocumentService vehicleDocumentService;

    public VehicleDocumentResource(VehicleDocumentService vehicleDocumentService) {
        this.vehicleDocumentService = vehicleDocumentService;
    }

    /**
     * POST  /vehicle-documents : Create a new vehicleDocument.
     *
     * @param vehicleDocumentDTO the vehicleDocumentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleDocumentDTO, or with status 400 (Bad Request) if the vehicleDocument has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-documents")
    public ResponseEntity<VehicleDocumentDTO> createVehicleDocument(@RequestBody VehicleDocumentDTO vehicleDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleDocument : {}", vehicleDocumentDTO);
        if (vehicleDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDocumentDTO result = vehicleDocumentService.save(vehicleDocumentDTO);
        return ResponseEntity.created(new URI("/api/vehicle-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-documents : Updates an existing vehicleDocument.
     *
     * @param vehicleDocumentDTO the vehicleDocumentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleDocumentDTO,
     * or with status 400 (Bad Request) if the vehicleDocumentDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleDocumentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-documents")
    public ResponseEntity<VehicleDocumentDTO> updateVehicleDocument(@RequestBody VehicleDocumentDTO vehicleDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleDocument : {}", vehicleDocumentDTO);
        if (vehicleDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VehicleDocumentDTO result = vehicleDocumentService.save(vehicleDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-documents : get all the vehicleDocuments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleDocuments in body
     */
    @GetMapping("/vehicle-documents")
    public ResponseEntity<List<VehicleDocumentDTO>> getAllVehicleDocuments(Pageable pageable) {
        log.debug("REST request to get a page of VehicleDocuments");
        Page<VehicleDocumentDTO> page = vehicleDocumentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicle-documents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /vehicle-documents/:id : get the "id" vehicleDocument.
     *
     * @param id the id of the vehicleDocumentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleDocumentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-documents/{id}")
    public ResponseEntity<VehicleDocumentDTO> getVehicleDocument(@PathVariable Long id) {
        log.debug("REST request to get VehicleDocument : {}", id);
        Optional<VehicleDocumentDTO> vehicleDocumentDTO = vehicleDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vehicleDocumentDTO);
    }

    /**
     * DELETE  /vehicle-documents/:id : delete the "id" vehicleDocument.
     *
     * @param id the id of the vehicleDocumentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-documents/{id}")
    public ResponseEntity<Void> deleteVehicleDocument(@PathVariable Long id) {
        log.debug("REST request to delete VehicleDocument : {}", id);
        vehicleDocumentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/vehicle-documents?query=:query : search for the vehicleDocument corresponding
     * to the query.
     *
     * @param query the query of the vehicleDocument search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/vehicle-documents")
    public ResponseEntity<List<VehicleDocumentDTO>> searchVehicleDocuments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VehicleDocuments for query {}", query);
        Page<VehicleDocumentDTO> page = vehicleDocumentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/vehicle-documents");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
