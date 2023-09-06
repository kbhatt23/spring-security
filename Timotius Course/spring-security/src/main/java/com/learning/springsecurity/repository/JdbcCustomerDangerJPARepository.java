package com.learning.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.learning.springsecurity.entity.JdbcCustomer;

//can help in quer sql
// but procedure call is very risky, even framework is not able to help

@Repository
public interface JdbcCustomerDangerJPARepository extends JpaRepository<JdbcCustomer, Integer> {
	
	public List<JdbcCustomer> findByEmail(String email);
	
	public List<JdbcCustomer> findByGender(String gender);
	
	//@Query("SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE gender = :email")
	//public List<JdbcCustomer> findByEmailCustom(@Param("email")  String email);
	
	@Query(value = "CALL update_jdbc_customer(:customerId, :newFullName)" , nativeQuery = true)
	void updateFullNameForCustomer(@Param("customerId") int customerId, @Param("newFullName") String newFullName);

}
