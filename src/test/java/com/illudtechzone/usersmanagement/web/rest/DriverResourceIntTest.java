package com.illudtechzone.usersmanagement.web.rest;

import com.illudtechzone.usersmanagement.UsersManagementApp;

import com.illudtechzone.usersmanagement.domain.Driver;
import com.illudtechzone.usersmanagement.repository.DriverRepository;
import com.illudtechzone.usersmanagement.repository.search.DriverSearchRepository;
import com.illudtechzone.usersmanagement.service.DriverService;
import com.illudtechzone.usersmanagement.service.dto.DriverDTO;
import com.illudtechzone.usersmanagement.service.mapper.DriverMapper;
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
 * Test class for the DriverResource REST controller.
 *
 * @see DriverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsersManagementApp.class)
public class DriverResourceIntTest {

    private static final String DEFAULT_DRIVER_IDP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_IDP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Double DEFAULT_TOTAL_RATING = 1D;
    private static final Double UPDATED_TOTAL_RATING = 2D;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    private DriverService driverService;

    /**
     * This repository is mocked in the com.illudtechzone.usersmanagement.repository.search test package.
     *
     * @see com.illudtechzone.usersmanagement.repository.search.DriverSearchRepositoryMockConfiguration
     */
    @Autowired
    private DriverSearchRepository mockDriverSearchRepository;

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

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DriverResource driverResource = new DriverResource(driverService);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
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
    public static Driver createEntity(EntityManager em) {
        Driver driver = new Driver()
            .driverIdpCode(DEFAULT_DRIVER_IDP_CODE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .location(DEFAULT_LOCATION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .totalRating(DEFAULT_TOTAL_RATING)
            .status(DEFAULT_STATUS);
        return driver;
    }

    @Before
    public void initTest() {
        driver = createEntity(em);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getDriverIdpCode()).isEqualTo(DEFAULT_DRIVER_IDP_CODE);
        assertThat(testDriver.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testDriver.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDriver.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testDriver.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testDriver.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testDriver.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testDriver.getTotalRating()).isEqualTo(DEFAULT_TOTAL_RATING);
        assertThat(testDriver.isStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Driver in Elasticsearch
        verify(mockDriverSearchRepository, times(1)).save(testDriver);
    }

    @Test
    @Transactional
    public void createDriverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver with an existing ID
        driver.setId(1L);
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDriverMockMvc.perform(post("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeCreate);

        // Validate the Driver in Elasticsearch
        verify(mockDriverSearchRepository, times(0)).save(driver);
    }

    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the driverList
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].driverIdpCode").value(hasItem(DEFAULT_DRIVER_IDP_CODE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].totalRating").value(hasItem(DEFAULT_TOTAL_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.driverIdpCode").value(DEFAULT_DRIVER_IDP_CODE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.totalRating").value(DEFAULT_TOTAL_RATING.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        Driver updatedDriver = driverRepository.findById(driver.getId()).get();
        // Disconnect from session so that the updates on updatedDriver are not directly saved in db
        em.detach(updatedDriver);
        updatedDriver
            .driverIdpCode(UPDATED_DRIVER_IDP_CODE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .location(UPDATED_LOCATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .totalRating(UPDATED_TOTAL_RATING)
            .status(UPDATED_STATUS);
        DriverDTO driverDTO = driverMapper.toDto(updatedDriver);

        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = driverList.get(driverList.size() - 1);
        assertThat(testDriver.getDriverIdpCode()).isEqualTo(UPDATED_DRIVER_IDP_CODE);
        assertThat(testDriver.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testDriver.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDriver.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testDriver.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testDriver.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testDriver.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testDriver.getTotalRating()).isEqualTo(UPDATED_TOTAL_RATING);
        assertThat(testDriver.isStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Driver in Elasticsearch
        verify(mockDriverSearchRepository, times(1)).save(testDriver);
    }

    @Test
    @Transactional
    public void updateNonExistingDriver() throws Exception {
        int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Create the Driver
        DriverDTO driverDTO = driverMapper.toDto(driver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDriverMockMvc.perform(put("/api/drivers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(driverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Driver in the database
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Driver in Elasticsearch
        verify(mockDriverSearchRepository, times(0)).save(driver);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Delete the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Driver> driverList = driverRepository.findAll();
        assertThat(driverList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Driver in Elasticsearch
        verify(mockDriverSearchRepository, times(1)).deleteById(driver.getId());
    }

    @Test
    @Transactional
    public void searchDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);
        when(mockDriverSearchRepository.search(queryStringQuery("id:" + driver.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(driver), PageRequest.of(0, 1), 1));
        // Search the driver
        restDriverMockMvc.perform(get("/api/_search/drivers?query=id:" + driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
            .andExpect(jsonPath("$.[*].driverIdpCode").value(hasItem(DEFAULT_DRIVER_IDP_CODE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].totalRating").value(hasItem(DEFAULT_TOTAL_RATING.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = new Driver();
        driver1.setId(1L);
        Driver driver2 = new Driver();
        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);
        driver2.setId(2L);
        assertThat(driver1).isNotEqualTo(driver2);
        driver1.setId(null);
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DriverDTO.class);
        DriverDTO driverDTO1 = new DriverDTO();
        driverDTO1.setId(1L);
        DriverDTO driverDTO2 = new DriverDTO();
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
        driverDTO2.setId(driverDTO1.getId());
        assertThat(driverDTO1).isEqualTo(driverDTO2);
        driverDTO2.setId(2L);
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
        driverDTO1.setId(null);
        assertThat(driverDTO1).isNotEqualTo(driverDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(driverMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(driverMapper.fromId(null)).isNull();
    }
}
