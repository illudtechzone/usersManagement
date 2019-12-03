package com.illudtechzone.usersmanagement.repository.search;

import com.illudtechzone.usersmanagement.domain.Vehicle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Vehicle entity.
 */
public interface VehicleSearchRepository extends ElasticsearchRepository<Vehicle, Long> {
}
