package com.example.security;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.controller.UserController;
import com.example.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JWTAuthorizationFilterTest {

	private MockMvc mockMvc;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	FilterChainProxy springSecurityFilterChain;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	}

	@Test
	public void shouldNotAllowAccessToUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").header("Authorization", "Bearer fail.fail.fail")
				.param("username", "username")).andExpect(status().is(401)).andDo(print());
	}

	@Test
	public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").param("username", "mock"))
				.andExpect(status().is(403)).andDo(print());
	}

}
