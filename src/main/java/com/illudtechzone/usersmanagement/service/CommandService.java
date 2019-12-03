package com.illudtechzone.usersmanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.illudtechzone.usersmanagement.domain.Driver;
import com.illudtechzone.usersmanagement.service.dto.CustomerDTO;
import com.illudtechzone.usersmanagement.service.dto.DriverDTO;

public interface CommandService {

	DriverDTO toDriverDto(Driver driver);

	List<DriverDTO> toDriverDtos(List<Driver> drivers);

	DriverDTO createDriverIfNotExist(DriverDTO driver);

	CustomerDTO createCustomerIfNotExist(CustomerDTO customerDto);

	DriverDTO updateDriverLocation(DriverDTO driverDTO);

}
