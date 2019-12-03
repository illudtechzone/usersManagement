package com.illudtechzone.usersmanagement.repository;

import com.illudtechzone.usersmanagement.domain.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Spring Data  repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findByCustomerIdpCode(String customerIdpode);

}
