package com.illudtechzone.usersmanagement.repository.search;

import com.illudtechzone.usersmanagement.domain.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Customer entity.
 */
public interface CustomerSearchRepository extends ElasticsearchRepository<Customer, Long> {
}
