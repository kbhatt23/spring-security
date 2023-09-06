package com.learning.springsecurity.controllers.sqlinjection;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learning.springsecurity.dtos.JdbcCustomerPathchRequest;
import com.learning.springsecurity.entity.JdbcCustomer;
import com.learning.springsecurity.repository.JdbcCustomerDangerJPARepository;


@RestController
@RequestMapping("/sqlinjection/danger-jpa/customers")
public class JdbcDangerJPAController {

	@Autowired
	private JdbcCustomerDangerJPARepository jdbcCustomerJPARepository;
	
	@GetMapping("/{email}")
	public JdbcCustomer findByEmail(@PathVariable String email) {
		return Optional.ofNullable(jdbcCustomerJPARepository.findByEmail(email))
		        .filter(customers -> !customers.isEmpty())
		        .map(customers -> customers.get(0))
		        .orElse(null);
	}
	
//	@GetMapping("/{email}/custom")
//	public JdbcCustomer findByEmailCustom(@PathVariable String email) {
//		return Optional.ofNullable(jdbcCustomerJPARepository.findByEmailCustom(email))
//		        .filter(customers -> !customers.isEmpty())
//		        .map(customers -> customers.get(0))
//		        .orElse(null);
//	}
	
	
	
	@GetMapping
	public List<JdbcCustomer> findByGender(@RequestParam(required = false) String gender) {
		if(StringUtils.isBlank(gender))
			return jdbcCustomerJPARepository.findAll();
		
		return jdbcCustomerJPARepository.findByGender(gender);
	}
	
	@PostMapping
	public String createCustomer(@RequestBody JdbcCustomer jdbcCustomer) {
		jdbcCustomerJPARepository.save(jdbcCustomer);
		 return "success";
	}
	
	@PatchMapping("/{customerID}")
	public String updateCustomer(@RequestBody JdbcCustomerPathchRequest jdbcCustomer, @PathVariable int customerID) {
		jdbcCustomerJPARepository.updateFullNameForCustomer(customerID , jdbcCustomer.getNewFullName());
		 return "success";
	}
}
