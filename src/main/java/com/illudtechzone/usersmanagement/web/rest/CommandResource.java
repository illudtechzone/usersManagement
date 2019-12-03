package com.illudtechzone.usersmanagement.web.rest;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.illudtechzone.usersmanagement.domain.Customer;
import com.illudtechzone.usersmanagement.domain.Driver;
import com.illudtechzone.usersmanagement.service.CommandService;
import com.illudtechzone.usersmanagement.service.dto.CustomerDTO;
import com.illudtechzone.usersmanagement.service.dto.DriverDTO;
import com.illudtechzone.usersmanagement.service.impl.CommandServiceImpl;

@RestController
@RequestMapping("/api/command")
public class CommandResource {
	
	@Autowired
	CommandService commandService;
	Logger log=Logger.getLogger(CommandServiceImpl.class);

	
	
	
	//////////////////////////////todto//////////////////////////
	@PostMapping("/toDto/Driver")
	public ResponseEntity<DriverDTO> toDriverDto(@RequestBody Driver driver){
		
		log.debug("#command resource#toDriverDTO#========driver===>"+driver);
		return new ResponseEntity<>(
				commandService.toDriverDto(driver), 
			      HttpStatus.OK);
		
		

	}
	
	@PostMapping("/toDto/Drivers")
	public ResponseEntity<List<DriverDTO>> toDriverDtos(@RequestBody List<Driver> drivers){
		log.debug("#command resource#toDriversDTO#=======drivers===>"+drivers);

		return new ResponseEntity<>(
				commandService.toDriverDtos(drivers), 
			      HttpStatus.OK);
		
		

	}
	
	//////////////////////////////TODTO//////////////////////////
	
	
   /////////////////////////////Driver////////////////////////////
	
	@PostMapping("/createIfNotExist/driver")
	public ResponseEntity<DriverDTO> createDriverIfNotExist(@RequestBody DriverDTO driver){
		log.debug("#command resource#createDriverIfNotExist#=======driver===>"+driver);

		return new ResponseEntity<>(
				commandService.createDriverIfNotExist(driver), 
			      HttpStatus.OK);
		
		

	}
	
	@PutMapping("/updateDriverStatus")
	public ResponseEntity<DriverDTO> updateDriverLocation(@RequestBody DriverDTO driverDTO){
		log.debug("#command resource#updateDriverLocation#=======driverDTO===>"+driverDTO);

		return new ResponseEntity<>(
				commandService.updateDriverLocation(driverDTO), HttpStatus.OK);
		
		

	}
	
	
	////////////////////////////////DRIVER//////////////////////////////
	

	   /////////////////////////////customer////////////////////////////
		
		@PostMapping("/createIfNotExist/customer")
		public ResponseEntity<CustomerDTO> createCustomerIfNotExist(@RequestBody CustomerDTO customerDto){
			log.debug("#command resource#createCustomerIfNotExist#=======customerDto===>"+customerDto);

			return new ResponseEntity<>(commandService.createCustomerIfNotExist(customerDto), HttpStatus.OK);
			
			

		}
		
		////////////////////////////////CUSTOMER//////////////////////////////
		
	

}
