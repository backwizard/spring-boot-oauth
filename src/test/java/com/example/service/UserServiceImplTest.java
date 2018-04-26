package com.example.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.MemberType;
import com.example.model.User;
import com.example.model.UserDetail;
import com.example.repository.UserRepository;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@InjectMocks
	private UserServiceImpl userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loadUserFound() {
		User fullUser = new User();
		fullUser.setId(1);
		fullUser.setUsername("username");
		fullUser.setPassword("$2a$10$grWguQU654XUd7kYDogjAeU31OU24F/11WEEpfXqEccgmc7br.7jC");
		fullUser.setAddress("test");
		fullUser.setSalary(new BigDecimal(50000));
		fullUser.setMemberType(MemberType.Platinum);
		fullUser.setPhone("0123456789");
		fullUser.setRefCode(userService.generateRefCode(fullUser.getPhone()));
		Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.of(fullUser));
		
		UserDetail userDeail = (UserDetail) userService.loadUserByUsername("username");
		assertEquals(fullUser.getId(), userDeail.getId());
		assertEquals(fullUser.getUsername(), userDeail.getUsername());
		assertEquals(fullUser.getPassword(), userDeail.getPassword());
		assertEquals(fullUser.getAddress(), userDeail.getAddress());
		assertEquals(fullUser.getMemberType(), userDeail.getMemberType());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserNotFound() {
		Mockito.when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
		userService.loadUserByUsername("username");
	}
	
	@Test
	public void saveMemberPlatinumUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setAddress("test");
		user.setSalary(new BigDecimal(50001));
		user.setPhone("0123456789");
		
		User fullUser = new User();
		fullUser.setId(1);
		fullUser.setUsername("username");
		fullUser.setPassword("$2a$10$grWguQU654XUd7kYDogjAeU31OU24F/11WEEpfXqEccgmc7br.7jC");
		fullUser.setAddress("test");
		fullUser.setSalary(new BigDecimal(50000));
		fullUser.setMemberType(MemberType.Platinum);
		fullUser.setPhone("0123456789");
		String dateNow = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		user.setRefCode(dateNow.concat(user.getPhone()));
		Mockito.when(userRepository.save(user)).thenReturn(fullUser);
		
		User result = userService.saveUser(user);
		System.out.println(result.toString());
		assertThat(result.getUsername()).isEqualTo(user.getUsername());
		assertEquals(result, fullUser);
	}
	
	@Test
	public void saveMemberGoldUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setAddress("test");
		user.setSalary(new BigDecimal(50000));
		user.setPhone("0123456789");
		
		User fullUser = new User();
		fullUser.setId(1);
		fullUser.setUsername("username");
		fullUser.setPassword("$2a$10$grWguQU654XUd7kYDogjAeU31OU24F/11WEEpfXqEccgmc7br.7jC");
		fullUser.setAddress("test");
		fullUser.setSalary(new BigDecimal(50000));
		fullUser.setMemberType(MemberType.Gold);
		fullUser.setPhone("0123456789");
		String dateNow = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		user.setRefCode(dateNow.concat(user.getPhone()));
		Mockito.when(userRepository.save(user)).thenReturn(fullUser);
		
		User result = userService.saveUser(user);
		System.out.println(result.toString());
		assertThat(result.getUsername()).isEqualTo(user.getUsername());
		assertEquals(result, fullUser);
	}
	
	@Test
	public void saveMemberSilverUser() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setAddress("test");
		user.setSalary(new BigDecimal(29000));
		user.setPhone("0123456789");
		
		User fullUser = new User();
		fullUser.setId(1);
		fullUser.setUsername("username");
		fullUser.setPassword("$2a$10$grWguQU654XUd7kYDogjAeU31OU24F/11WEEpfXqEccgmc7br.7jC");
		fullUser.setAddress("test");
		fullUser.setSalary(new BigDecimal(29000));
		fullUser.setMemberType(MemberType.Gold);
		fullUser.setPhone("0123456789");
		String dateNow = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		user.setRefCode(dateNow.concat(user.getPhone()));
		Mockito.when(userRepository.save(user)).thenReturn(fullUser);
		
		User result = userService.saveUser(user);
		assertThat(result.getUsername()).isEqualTo(user.getUsername());
		assertEquals(result, fullUser);
	}
	
	@Test(expected = RuntimeException.class)
	public void saveUserDupplicate() {
		User user = new User();
		user.setUsername("x");
		when(userRepository.save(user)).thenThrow(new DataIntegrityViolationException("Error"));
		userService.saveUser(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void saveUserSalaryTooLow() {
		User user = new User();
		user.setUsername("username");
		user.setPassword("password");
		user.setAddress("test");
		user.setSalary(new BigDecimal(13999));
		user.setPhone("0123456789");
		userService.saveUser(user);
	}
}
