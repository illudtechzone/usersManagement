package com.illudtechzone.usersmanagement.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DriverDocumentSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DriverDocumentSearchRepositoryMockConfiguration {

    @MockBean
    private DriverDocumentSearchRepository mockDriverDocumentSearchRepository;

}
