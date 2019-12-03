package com.illudtechzone.usersmanagement.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of VehicleDocumentSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class VehicleDocumentSearchRepositoryMockConfiguration {

    @MockBean
    private VehicleDocumentSearchRepository mockVehicleDocumentSearchRepository;

}
