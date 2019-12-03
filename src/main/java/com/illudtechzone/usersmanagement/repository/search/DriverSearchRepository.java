package com.illudtechzone.usersmanagement.repository.search;

import com.illudtechzone.usersmanagement.domain.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Driver entity.
 */
public interface DriverSearchRepository extends ElasticsearchRepository<Driver, Long> {
}
