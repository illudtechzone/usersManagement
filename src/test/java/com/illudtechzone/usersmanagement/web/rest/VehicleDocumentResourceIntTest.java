package com.illudtechzone.usersmanagement.web.rest;

import com.illudtechzone.usersmanagement.UsersManagementApp;

import com.illudtechzone.usersmanagement.domain.VehicleDocument;
import com.illudtechzone.usersmanagement.repository.VehicleDocumentRepository;
import com.illudtechzone.usersmanagement.repository.search.VehicleDocumentSearchRepository;
import com.illudtechzone.usersmanagement.service.VehicleDocumentService;
import com.illudtechzone.usersmanagement.service.dto.VehicleDocumentDTO;
import com.illudtechzone.usersmanagement.service.mapper.VehicleDocumentMapper;
import com.illudtechzone.usersmanagement.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;


import static com.illudtechzone.usersmanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VehicleDocumentResource REST controller.
 *
 * @see VehicleDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsersManagementApp.class)
public class VehicleDocumentResourceIntTest {

    private static final String DEFAULT_DOCUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_TYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_UPLOAD_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_VALIDATAION_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALIDATAION_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_EXPIRED = false;
    private static final Boolean UPDATED_IS_EXPIRED = true;

    @Autowired
    private VehicleDocumentRepository vehicleDocumentRepository;

    @Autowired
    private VehicleDocumentMapper vehicleDocumentMapper;

    @Autowired
    private VehicleDocumentService vehicleDocumentService;

    /**
     * This repository is mocked in the com.illudtechzone.usersmanagement.repository.search test package.
     *
     * @see com.illudtechzone.usersmanagement.repository.search.VehicleDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private VehicleDocumentSearchRepository mockVehicleDocumentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restVehicleDocumentMockMvc;

    private VehicleDocument vehicleDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleDocumentResource vehicleDocumentResource = new VehicleDocumentResource(vehicleDocumentService);
        this.restVehicleDocumentMockMvc = MockMvcBuilders.standaloneSetup(vehicleDocumentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleDocument createEntity(EntityManager em) {
        VehicleDocument vehicleDocument = new VehicleDocument()
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE)
            .uploadTime(DEFAULT_UPLOAD_TIME)
            .validataionStartDate(DEFAULT_VALIDATAION_START_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .isExpired(DEFAULT_IS_EXPIRED);
        return vehicleDocument;
    }

    @Before
    public void initTest() {
        vehicleDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleDocument() throws Exception {
        int databaseSizeBeforeCreate = vehicleDocumentRepository.findAll().size();

        // Create the VehicleDocument
        VehicleDocumentDTO vehicleDocumentDTO = vehicleDocumentMapper.toDto(vehicleDocument);
        restVehicleDocumentMockMvc.perform(post("/api/vehicle-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleDocument in the database
        List<VehicleDocument> vehicleDocumentList = vehicleDocumentRepository.findAll();
        assertThat(vehicleDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleDocument testVehicleDocument = vehicleDocumentList.get(vehicleDocumentList.size() - 1);
        assertThat(testVehicleDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testVehicleDocument.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testVehicleDocument.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
        assertThat(testVehicleDocument.getUploadTime()).isEqualTo(DEFAULT_UPLOAD_TIME);
        assertThat(testVehicleDocument.getValidataionStartDate()).isEqualTo(DEFAULT_VALIDATAION_START_DATE);
        assertThat(testVehicleDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testVehicleDocument.isIsExpired()).isEqualTo(DEFAULT_IS_EXPIRED);

        // Validate the VehicleDocument in Elasticsearch
        verify(mockVehicleDocumentSearchRepository, times(1)).save(testVehicleDocument);
    }

    @Test
    @Transactional
    public void createVehicleDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleDocumentRepository.findAll().size();

        // Create the VehicleDocument with an existing ID
        vehicleDocument.setId(1L);
        VehicleDocumentDTO vehicleDocumentDTO = vehicleDocumentMapper.toDto(vehicleDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleDocumentMockMvc.perform(post("/api/vehicle-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDocument in the database
        List<VehicleDocument> vehicleDocumentList = vehicleDocumentRepository.findAll();
        assertThat(vehicleDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the VehicleDocument in Elasticsearch
        verify(mockVehicleDocumentSearchRepository, times(0)).save(vehicleDocument);
    }

    @Test
    @Transactional
    public void getAllVehicleDocuments() throws Exception {
        // Initialize the database
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        // Get all the vehicleDocumentList
        restVehicleDocumentMockMvc.perform(get("/api/vehicle-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].validataionStartDate").value(hasItem(DEFAULT_VALIDATAION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getVehicleDocument() throws Exception {
        // Initialize the database
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        // Get the vehicleDocument
        restVehicleDocumentMockMvc.perform(get("/api/vehicle-documents/{id}", vehicleDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentType").value(DEFAULT_DOCUMENT_TYPE.toString()))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.uploadTime").value(DEFAULT_UPLOAD_TIME.toString()))
            .andExpect(jsonPath("$.validataionStartDate").value(DEFAULT_VALIDATAION_START_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.isExpired").value(DEFAULT_IS_EXPIRED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleDocument() throws Exception {
        // Get the vehicleDocument
        restVehicleDocumentMockMvc.perform(get("/api/vehicle-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleDocument() throws Exception {
        // Initialize the database
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        int databaseSizeBeforeUpdate = vehicleDocumentRepository.findAll().size();

        // Update the vehicleDocument
        VehicleDocument updatedVehicleDocument = vehicleDocumentRepository.findById(vehicleDocument.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleDocument are not directly saved in db
        em.detach(updatedVehicleDocument);
        updatedVehicleDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .uploadTime(UPDATED_UPLOAD_TIME)
            .validataionStartDate(UPDATED_VALIDATAION_START_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .isExpired(UPDATED_IS_EXPIRED);
        VehicleDocumentDTO vehicleDocumentDTO = vehicleDocumentMapper.toDto(updatedVehicleDocument);

        restVehicleDocumentMockMvc.perform(put("/api/vehicle-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleDocument in the database
        List<VehicleDocument> vehicleDocumentList = vehicleDocumentRepository.findAll();
        assertThat(vehicleDocumentList).hasSize(databaseSizeBeforeUpdate);
        VehicleDocument testVehicleDocument = vehicleDocumentList.get(vehicleDocumentList.size() - 1);
        assertThat(testVehicleDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testVehicleDocument.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testVehicleDocument.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
        assertThat(testVehicleDocument.getUploadTime()).isEqualTo(UPDATED_UPLOAD_TIME);
        assertThat(testVehicleDocument.getValidataionStartDate()).isEqualTo(UPDATED_VALIDATAION_START_DATE);
        assertThat(testVehicleDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testVehicleDocument.isIsExpired()).isEqualTo(UPDATED_IS_EXPIRED);

        // Validate the VehicleDocument in Elasticsearch
        verify(mockVehicleDocumentSearchRepository, times(1)).save(testVehicleDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleDocument() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDocumentRepository.findAll().size();

        // Create the VehicleDocument
        VehicleDocumentDTO vehicleDocumentDTO = vehicleDocumentMapper.toDto(vehicleDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleDocumentMockMvc.perform(put("/api/vehicle-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleDocument in the database
        List<VehicleDocument> vehicleDocumentList = vehicleDocumentRepository.findAll();
        assertThat(vehicleDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VehicleDocument in Elasticsearch
        verify(mockVehicleDocumentSearchRepository, times(0)).save(vehicleDocument);
    }

    @Test
    @Transactional
    public void deleteVehicleDocument() throws Exception {
        // Initialize the database
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);

        int databaseSizeBeforeDelete = vehicleDocumentRepository.findAll().size();

        // Delete the vehicleDocument
        restVehicleDocumentMockMvc.perform(delete("/api/vehicle-documents/{id}", vehicleDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleDocument> vehicleDocumentList = vehicleDocumentRepository.findAll();
        assertThat(vehicleDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VehicleDocument in Elasticsearch
        verify(mockVehicleDocumentSearchRepository, times(1)).deleteById(vehicleDocument.getId());
    }

    @Test
    @Transactional
    public void searchVehicleDocument() throws Exception {
        // Initialize the database
        vehicleDocumentRepository.saveAndFlush(vehicleDocument);
        when(mockVehicleDocumentSearchRepository.search(queryStringQuery("id:" + vehicleDocument.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(vehicleDocument), PageRequest.of(0, 1), 1));
        // Search the vehicleDocument
        restVehicleDocumentMockMvc.perform(get("/api/_search/vehicle-documents?query=id:" + vehicleDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentType").value(hasItem(DEFAULT_DOCUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].validataionStartDate").value(hasItem(DEFAULT_VALIDATAION_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].isExpired").value(hasItem(DEFAULT_IS_EXPIRED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDocument.class);
        VehicleDocument vehicleDocument1 = new VehicleDocument();
        vehicleDocument1.setId(1L);
        VehicleDocument vehicleDocument2 = new VehicleDocument();
        vehicleDocument2.setId(vehicleDocument1.getId());
        assertThat(vehicleDocument1).isEqualTo(vehicleDocument2);
        vehicleDocument2.setId(2L);
        assertThat(vehicleDocument1).isNotEqualTo(vehicleDocument2);
        vehicleDocument1.setId(null);
        assertThat(vehicleDocument1).isNotEqualTo(vehicleDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDocumentDTO.class);
        VehicleDocumentDTO vehicleDocumentDTO1 = new VehicleDocumentDTO();
        vehicleDocumentDTO1.setId(1L);
        VehicleDocumentDTO vehicleDocumentDTO2 = new VehicleDocumentDTO();
        assertThat(vehicleDocumentDTO1).isNotEqualTo(vehicleDocumentDTO2);
        vehicleDocumentDTO2.setId(vehicleDocumentDTO1.getId());
        assertThat(vehicleDocumentDTO1).isEqualTo(vehicleDocumentDTO2);
        vehicleDocumentDTO2.setId(2L);
        assertThat(vehicleDocumentDTO1).isNotEqualTo(vehicleDocumentDTO2);
        vehicleDocumentDTO1.setId(null);
        assertThat(vehicleDocumentDTO1).isNotEqualTo(vehicleDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleDocumentMapper.fromId(null)).isNull();
    }
}
