package com.learning.springsecurity.controllers.sqlinjection;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

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
import com.learning.springsecurity.repository.JdbcCustomerSafeRepository;


@RestController
@RequestMapping("/sqlinjection/safe/customers")
public class JdbcSafeController {

	@Autowired
	private JdbcCustomerSafeRepository jdbcCustomerSafeRepository;
	
	@GetMapping("/{email}")
	public JdbcCustomer findByEmail(@PathVariable @Email @Valid String email) {
		return jdbcCustomerSafeRepository.findByEmail(email);
	}
	
	@GetMapping
	public List<JdbcCustomer> findByGender(@RequestParam(required = false) @Valid @Pattern(regexp = "^[MF]$", message = "Invalid gender") String gender) {
		if(StringUtils.isBlank(gender))
			return jdbcCustomerSafeRepository.findAll();
		
		return jdbcCustomerSafeRepository.findByGender(gender);
	}
	
	@PostMapping
	public String createCustomer(@RequestBody(required = true) @Valid JdbcCustomer jdbcCustomer) {
		jdbcCustomerSafeRepository.createCustomer(jdbcCustomer);
		 return "success";
	}
	
	@PatchMapping("/{customerID}")
	public String updateCustomer(@RequestBody JdbcCustomerPathchRequest jdbcCustomer, @PathVariable int customerID) {
		jdbcCustomerSafeRepository.updateCustomerFullName(customerID , jdbcCustomer.getNewFullName());
		 return "success";
	}
}
