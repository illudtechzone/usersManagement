package com.illudtechzone.usersmanagement.repository;

import com.illudtechzone.usersmanagement.domain.VehicleDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VehicleDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, Long> {

}
