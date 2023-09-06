package com.learning.springsecurity.controllers.sqlinjection;

import java.util.List;

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
import com.learning.springsecurity.repository.JdbcCustomerDangerousRepository;


@RestController
@RequestMapping("/sqlinjection/danger/customers")
public class JdbcDangerController {

	@Autowired
	private JdbcCustomerDangerousRepository jdbcCustomerDangerousRepository;
	
	@GetMapping("/{email}")
	public JdbcCustomer findByEmail(@PathVariable String email) {
		return jdbcCustomerDangerousRepository.findByEmail(email);
	}
	
	@GetMapping
	public List<JdbcCustomer> findByGender(@RequestParam(required = false) String gender) {
		if(StringUtils.isBlank(gender))
			return jdbcCustomerDangerousRepository.findAll();
		
		return jdbcCustomerDangerousRepository.findByGender(gender);
	}
	
	@PostMapping
	public String createCustomer(@RequestBody(required = true) JdbcCustomer jdbcCustomer) {
		 jdbcCustomerDangerousRepository.createCustomer(jdbcCustomer);
		 return "success";
	}
	
	@PatchMapping("/{customerID}")
	public String updateCustomer(@RequestBody JdbcCustomerPathchRequest jdbcCustomer, @PathVariable int customerID) {
		 jdbcCustomerDangerousRepository.updateCustomerFullName(customerID , jdbcCustomer.getNewFullName());
		 return "success";
	}
}
