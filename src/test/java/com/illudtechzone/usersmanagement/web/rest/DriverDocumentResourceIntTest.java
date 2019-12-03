package com.illudtechzone.usersmanagement.web.rest;

import com.illudtechzone.usersmanagement.UsersManagementApp;

import com.illudtechzone.usersmanagement.domain.DriverDocument;
import com.illudtechzone.usersmanagement.repository.DriverDocumentRepository;
import com.illudtechzone.usersmanagement.repository.search.DriverDocumentSearchRepository;
import com.illudtechzone.usersmanagement.service.DriverDocumentService;
import com.illudtechzone.usersmanagement.service.dto.DriverDocumentDTO;
import com.illudtechzone.usersmanagement.service.mapper.DriverDocumentMapper;
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
 * Test class for the DriverDocumentResource REST controller.
 *
 * @see DriverDocumentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsersManagementApp.class)
public class DriverDocumentResourceIntTest {

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
    private DriverDocumentRepository driverDocumentRepository;

    @Autowired
    private DriverDocumentMapper driverDocumentMapper;

    @Autowired
    private DriverDocumentService driverDocumentService;

    /**
     * This repository is mocked in the com.illudtechzone.usersmanagement.repository.search test package.
     *
     * @see com.illudtechzone.usersmanagement.repository.search.DriverDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private DriverDocumentSearchRepository mockDriverDocumentSearchRepository;

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

    private MockMvc restDriverDocumentMockMvc;

    private DriverDocument driverDocument;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriverDocumentResource driverDocumentResource = new DriverDocumentResource(driverDocumentService);
        this.restDriverDocumentMockMvc = MockMvcBuilders.standaloneSetup(driverDocumentResource)
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
    public static DriverDocument createEntity(EntityManager em) {
        DriverDocument driverDocument = new DriverDocument()
            .documentType(DEFAULT_DOCUMENT_TYPE)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE)
            .uploadTime(DEFAULT_UPLOAD_TIME)
            .validataionStartDate(DEFAULT_VALIDATAION_START_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .isExpired(DEFAULT_IS_EXPIRED);
        return driverDocument;
    }

    @Before
    public void initTest() {
        driverDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriverDocument() throws Exception {
        int databaseSizeBeforeCreate = driverDocumentRepository.findAll().size();

        // Create the DriverDocument
        DriverDocumentDTO driverDocumentDTO = driverDocumentMapper.toDto(driverDocument);
        restDriverDocumentMockMvc.perform(post("/api/driver-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the DriverDocument in the database
        List<DriverDocument> driverDocumentList = driverDocumentRepository.findAll();
        assertThat(driverDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        DriverDocument testDriverDocument = driverDocumentList.get(driverDocumentList.size() - 1);
        assertThat(testDriverDocument.getDocumentType()).isEqualTo(DEFAULT_DOCUMENT_TYPE);
        assertThat(testDriverDocument.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testDriverDocument.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
        assertThat(testDriverDocument.getUploadTime()).isEqualTo(DEFAULT_UPLOAD_TIME);
        assertThat(testDriverDocument.getValidataionStartDate()).isEqualTo(DEFAULT_VALIDATAION_START_DATE);
        assertThat(testDriverDocument.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testDriverDocument.isIsExpired()).isEqualTo(DEFAULT_IS_EXPIRED);

        // Validate the DriverDocument in Elasticsearch
        verify(mockDriverDocumentSearchRepository, times(1)).save(testDriverDocument);
    }

    @Test
    @Transactional
    public void createDriverDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverDocumentRepository.findAll().size();

        // Create the DriverDocument with an existing ID
        driverDocument.setId(1L);
        DriverDocumentDTO driverDocumentDTO = driverDocumentMapper.toDto(driverDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverDocumentMockMvc.perform(post("/api/driver-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DriverDocument in the database
        List<DriverDocument> driverDocumentList = driverDocumentRepository.findAll();
        assertThat(driverDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the DriverDocument in Elasticsearch
        verify(mockDriverDocumentSearchRepository, times(0)).save(driverDocument);
    }

    @Test
    @Transactional
    public void getAllDriverDocuments() throws Exception {
        // Initialize the database
        driverDocumentRepository.saveAndFlush(driverDocument);

        // Get all the driverDocumentList
        restDriverDocumentMockMvc.perform(get("/api/driver-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driverDocument.getId().intValue())))
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
    public void getDriverDocument() throws Exception {
        // Initialize the database
        driverDocumentRepository.saveAndFlush(driverDocument);

        // Get the driverDocument
        restDriverDocumentMockMvc.perform(get("/api/driver-documents/{id}", driverDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driverDocument.getId().intValue()))
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
    public void getNonExistingDriverDocument() throws Exception {
        // Get the driverDocument
        restDriverDocumentMockMvc.perform(get("/api/driver-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriverDocument() throws Exception {
        // Initialize the database
        driverDocumentRepository.saveAndFlush(driverDocument);

        int databaseSizeBeforeUpdate = driverDocumentRepository.findAll().size();

        // Update the driverDocument
        DriverDocument updatedDriverDocument = driverDocumentRepository.findById(driverDocument.getId()).get();
        // Disconnect from session so that the updates on updatedDriverDocument are not directly saved in db
        em.detach(updatedDriverDocument);
        updatedDriverDocument
            .documentType(UPDATED_DOCUMENT_TYPE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .uploadTime(UPDATED_UPLOAD_TIME)
            .validataionStartDate(UPDATED_VALIDATAION_START_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .isExpired(UPDATED_IS_EXPIRED);
        DriverDocumentDTO driverDocumentDTO = driverDocumentMapper.toDto(updatedDriverDocument);

        restDriverDocumentMockMvc.perform(put("/api/driver-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the DriverDocument in the database
        List<DriverDocument> driverDocumentList = driverDocumentRepository.findAll();
        assertThat(driverDocumentList).hasSize(databaseSizeBeforeUpdate);
        DriverDocument testDriverDocument = driverDocumentList.get(driverDocumentList.size() - 1);
        assertThat(testDriverDocument.getDocumentType()).isEqualTo(UPDATED_DOCUMENT_TYPE);
        assertThat(testDriverDocument.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testDriverDocument.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
        assertThat(testDriverDocument.getUploadTime()).isEqualTo(UPDATED_UPLOAD_TIME);
        assertThat(testDriverDocument.getValidataionStartDate()).isEqualTo(UPDATED_VALIDATAION_START_DATE);
        assertThat(testDriverDocument.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testDriverDocument.isIsExpired()).isEqualTo(UPDATED_IS_EXPIRED);

        // Validate the DriverDocument in Elasticsearch
        verify(mockDriverDocumentSearchRepository, times(1)).save(testDriverDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingDriverDocument() throws Exception {
        int databaseSizeBeforeUpdate = driverDocumentRepository.findAll().size();

        // Create the DriverDocument
        DriverDocumentDTO driverDocumentDTO = driverDocumentMapper.toDto(driverDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverDocumentMockMvc.perform(put("/api/driver-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DriverDocument in the database
        List<DriverDocument> driverDocumentList = driverDocumentRepository.findAll();
        assertThat(driverDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DriverDocument in Elasticsearch
        verify(mockDriverDocumentSearchRepository, times(0)).save(driverDocument);
    }

    @Test
    @Transactional
    public void deleteDriverDocument() throws Exception {
        // Initialize the database
        driverDocumentRepository.saveAndFlush(driverDocument);

        int databaseSizeBeforeDelete = driverDocumentRepository.findAll().size();

        // Delete the driverDocument
        restDriverDocumentMockMvc.perform(delete("/api/driver-documents/{id}", driverDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DriverDocument> driverDocumentList = driverDocumentRepository.findAll();
        assertThat(driverDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DriverDocument in Elasticsearch
        verify(mockDriverDocumentSearchRepository, times(1)).deleteById(driverDocument.getId());
    }

    @Test
    @Transactional
    public void searchDriverDocument() throws Exception {
        // Initialize the database
        driverDocumentRepository.saveAndFlush(driverDocument);
        when(mockDriverDocumentSearchRepository.search(queryStringQuery("id:" + driverDocument.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(driverDocument), PageRequest.of(0, 1), 1));
        // Search the driverDocument
        restDriverDocumentMockMvc.perform(get("/api/_search/driver-documents?query=id:" + driverDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driverDocument.getId().intValue())))
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
        TestUtil.equalsVerifier(DriverDocument.class);
        DriverDocument driverDocument1 = new DriverDocument();
        driverDocument1.setId(1L);
        DriverDocument driverDocument2 = new DriverDocument();
        driverDocument2.setId(driverDocument1.getId());
        assertThat(driverDocument1).isEqualTo(driverDocument2);
        driverDocument2.setId(2L);
        assertThat(driverDocument1).isNotEqualTo(driverDocument2);
        driverDocument1.setId(null);
        assertThat(driverDocument1).isNotEqualTo(driverDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverDocumentDTO.class);
        DriverDocumentDTO driverDocumentDTO1 = new DriverDocumentDTO();
        driverDocumentDTO1.setId(1L);
        DriverDocumentDTO driverDocumentDTO2 = new DriverDocumentDTO();
        assertThat(driverDocumentDTO1).isNotEqualTo(driverDocumentDTO2);
        driverDocumentDTO2.setId(driverDocumentDTO1.getId());
        assertThat(driverDocumentDTO1).isEqualTo(driverDocumentDTO2);
        driverDocumentDTO2.setId(2L);
        assertThat(driverDocumentDTO1).isNotEqualTo(driverDocumentDTO2);
        driverDocumentDTO1.setId(null);
        assertThat(driverDocumentDTO1).isNotEqualTo(driverDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(driverDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(driverDocumentMapper.fromId(null)).isNull();
    }
}
