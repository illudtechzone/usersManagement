package com.illudtechzone.usersmanagement.repository.search;

import com.illudtechzone.usersmanagement.domain.VehicleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the VehicleDocument entity.
 */
public interface VehicleDocumentSearchRepository extends ElasticsearchRepository<VehicleDocument, Long> {
}
