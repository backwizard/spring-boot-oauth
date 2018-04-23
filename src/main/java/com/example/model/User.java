package com.example.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity(name = "user")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	private String address;

	@NotNull
	private BigDecimal salary;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_type")
	private MemberType memberType;

	@NotEmpty
	@Pattern(regexp = "(^$|[0-9]{10})")
	@Size(min = 10, max = 10)
	private String phone;

	@Column(name = "ref_code")
	private String refCode;
}
