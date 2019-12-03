package com.illudtechzone.usersmanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.illudtechzone.usersmanagement.domain.Customer;
import com.illudtechzone.usersmanagement.domain.Driver;
import com.illudtechzone.usersmanagement.repository.CustomerRepository;
import com.illudtechzone.usersmanagement.repository.DriverRepository;
import com.illudtechzone.usersmanagement.repository.search.CustomerSearchRepository;
import com.illudtechzone.usersmanagement.repository.search.DriverSearchRepository;
import com.illudtechzone.usersmanagement.service.CommandService;
import com.illudtechzone.usersmanagement.service.dto.CustomerDTO;
import com.illudtechzone.usersmanagement.service.dto.DriverDTO;
import com.illudtechzone.usersmanagement.service.mapper.CustomerMapper;
import com.illudtechzone.usersmanagement.service.mapper.DriverMapper;


@Service
public class CommandServiceImpl implements CommandService {

	Logger log=Logger.getLogger(CommandServiceImpl.class);
	
	@Autowired
    private  DriverSearchRepository driverSearchRepository;
	
	@Autowired
    private  CustomerSearchRepository customerSearchRepository;

	
	@Autowired
	DriverMapper driverMapper;
	
	@Autowired
	DriverRepository driverRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public DriverDTO toDriverDto(Driver driver) {
		
		return driverMapper.toDto(driver);
	
	}

	@Override
	public List<DriverDTO> toDriverDtos(List<Driver> drivers) {
		
		return driverMapper.toDto(drivers);

		
		
	}

	@Override
	public DriverDTO createDriverIfNotExist(DriverDTO driverDTO) {
		
		Optional<Driver> resutl =this.driverRepository.findByDriverIdpCode(driverDTO.getDriverIdpCode());
		
		if(resutl.isPresent()) {
			return driverMapper.toDto( resutl.get());
		}
		else
		{
			Driver driver=driverRepository.save(driverMapper.toEntity(driverDTO));
			driverSearchRepository.save(driver);
			return  driverMapper.toDto(driver);
		}
		
		
	}

	@Override
	public CustomerDTO createCustomerIfNotExist(CustomerDTO customerDto) {
Optional<Customer> resutl =this.customerRepository.findByCustomerIdpCode(customerDto.getCustomerIdpCode());
		
		if(resutl.isPresent()) {
			return customerMapper.toDto( resutl.get());
		}
		else
		{
			Customer customer=customerRepository.save(customerMapper.toEntity(customerDto));
			customerSearchRepository.save(customer);
			return  customerMapper.toDto(customer);
			
		}
	}

	@Override
	public DriverDTO updateDriverLocation(DriverDTO driverDTO) {
		Optional<Driver> resutl =this.driverRepository.findByDriverIdpCode(driverDTO.getDriverIdpCode());
		
		Driver driver=resutl.get();
		driver.setLocation(driverDTO.getLocation());
		
		driver.setStatus(driverDTO.isStatus());

		
		
		driverRepository.save(driver);
		driverSearchRepository.save(driver);
		
		log.debug("updating driver info >>>>>>>>>>>>>>>>"+driver);
		
		return  driverMapper.toDto(driver);
		
	}

	
	
	
	

}
