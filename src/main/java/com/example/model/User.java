package com.example.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

//import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Entity(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private long id;

	@NotEmpty
	@Column(unique = true, nullable = false)
	private String username;

	@NotEmpty
	@Column(nullable = false)
	private String password;

	@NotEmpty
	@Column(nullable = false)
	@JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
	private String address;

	@NotNull
	@Column(nullable = false)
	private BigDecimal salary;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_type", nullable = false)
	private MemberType memberType;

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	@Size(min = 10, max = 10)
	@Column(nullable = false)
	private String phone;

	@Column(name = "ref_code", nullable = false)
	private String refCode;

	public User() {
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.address = user.address;
		this.salary = user.salary;
		this.memberType = user.memberType;
		this.phone = user.phone;
		this.refCode = user.refCode;
	}
}
