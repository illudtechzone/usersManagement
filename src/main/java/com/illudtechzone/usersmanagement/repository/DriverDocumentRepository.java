package com.illudtechzone.usersmanagement.repository;

import com.illudtechzone.usersmanagement.domain.DriverDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DriverDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DriverDocumentRepository extends JpaRepository<DriverDocument, Long> {

}
