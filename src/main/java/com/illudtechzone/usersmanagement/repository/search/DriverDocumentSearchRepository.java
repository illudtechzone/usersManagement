package com.illudtechzone.usersmanagement.repository.search;

import com.illudtechzone.usersmanagement.domain.DriverDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DriverDocument entity.
 */
public interface DriverDocumentSearchRepository extends ElasticsearchRepository<DriverDocument, Long> {
}
