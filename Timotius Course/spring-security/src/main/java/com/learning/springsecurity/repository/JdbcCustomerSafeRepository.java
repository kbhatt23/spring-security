package com.learning.springsecurity.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.learning.springsecurity.entity.JdbcCustomer;

//prepared statement is safe with ?
// but when there are so many placeholder / ? we can use NamedParameterJdbcTemplate
@Repository
public class JdbcCustomerSafeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCustomer findByEmail(String email) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE email = ?"
				+ " AND email is not null";

		return jdbcTemplate.queryForObject(sql, this::mapToCustomer , email);
	}

	public List<JdbcCustomer> findByGender(String gender) {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer WHERE gender = ?"
				+ " AND gender is not null";

		return jdbcTemplate.query(sql, this::mapToCustomer, gender);
	}

	public List<JdbcCustomer> findAll() {
		var sql = "SELECT customer_id, full_name, email, birth_date, gender FROM jdbc_customer"
				+ " WHERE gender is not null";

		return jdbcTemplate.query(sql, this::mapToCustomer);
	}

	private JdbcCustomer mapToCustomer(ResultSet rs, long rowNum) throws SQLException {
		var customer = new JdbcCustomer();

		Date date = rs.getDate("birth_date");
		if (date != null) {
			customer.setBirthDate(date.toLocalDate());
		}
		customer.setCustomerId(rs.getInt("customer_id"));
		customer.setEmail(rs.getString("email"));
		customer.setFullName(rs.getString("full_name"));
		customer.setGender(rs.getString("gender"));

		return customer;
	}

	public void createCustomer(JdbcCustomer newCustomer) {
		var sql = "INSERT INTO jdbc_customer(full_name, email, gender, birth_date) " + "VALUES (:fullName, :email, :gender, :birthDate)";

		MapSqlParameterSource paramValueSource = new MapSqlParameterSource().addValue("fullName", newCustomer.getFullName())
		                   .addValue("email", newCustomer.getEmail())
		                   .addValue("gender", newCustomer.getGender())
		                   .addValue("birthDate", newCustomer.getBirthDate());
		namedParameterJdbcTemplate.update(sql,paramValueSource);
	}

	public void updateCustomerFullName(int customerId, String newFullName) {
		var sql = "CALL update_jdbc_customer(:customerID , :newFullName)";
		
		MapSqlParameterSource addValue = new MapSqlParameterSource()
		   .addValue("customerID", customerId)
		   .addValue("newFullName", newFullName);

		namedParameterJdbcTemplate.update(sql,addValue);
	}
}
