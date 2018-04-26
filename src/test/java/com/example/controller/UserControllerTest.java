package com.example.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.model.MemberType;
import com.example.security.SecurityUtils;
import com.example.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void verifySaveUserTooLessSalary() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"username\", \"password\" : \"password\","
						+ " \"address\" : \"address\" , \"salary\" : 10000, \"phone\": \"0123456789\" }")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.errorCode").value(400))
				.andExpect(jsonPath("$.message").value("Too less salary!"));
	}

	@Test
	public void verifySaveUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"username\", \"password\" : \"password\","
						+ " \"address\" : \"address\" , \"salary\" : 50000, \"phone\": \"0123456789\" }")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.status").value(201))
				.andExpect(jsonPath("$.message").value("Register success"));
	}

	@Test
	public void verifyMalformedSaveUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/register").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"username\": \"username\", \"password\" : \"password\","
						+ " \"address\" : \"address\" , \"salary\" : 50000 }")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(jsonPath("$.errorCode").value(400))
				.andExpect(jsonPath("$.message").value("Some fileds are missing!!"));
	}

	@Test
	public void verifyUser() throws Exception {
		String token = SecurityUtils.generateToken("x");
		String username = "username";
		assertNotNull(token);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token).param("username", username)).andDo(print())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.username").value("username"))
				 .andExpect(jsonPath("$.address").value("address"))
				.andExpect(jsonPath("$.salary").value(50000))
				.andExpect(jsonPath("$.memberType").value(MemberType.Gold.toString()))
				.andExpect(jsonPath("$.phone").value("0123456789"));
	}

	@Test
	public void verifyInvalidParam() throws Exception {
		String token = SecurityUtils.generateToken("x");
		String username = "username";
		assertNotNull(token);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token).param("%20", username)).andDo(print())
				.andExpect(jsonPath("$.errorCode").value(400)).andExpect(jsonPath("$.message")
						.value("The request could not be understood by the server due to malformed syntax."));
	}

	@Test
	public void verifyNullUser() throws Exception {
		String token = SecurityUtils.generateToken("x");
		String username = "username";
		assertNotNull(token);

		when(userService.loadUserByUsername(username)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token).param("username", "xyz")).andDo(print())
				.andExpect(jsonPath("$.errorCode").value(400))
				.andExpect(jsonPath("$.message").value("Username not found"));
	}

	@Test
	public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").param("username", "xzdd")).andExpect(status().is(400)).andDo(print());
	}

	@Test
	public void shouldGenerateAuthToken() throws Exception {
		String token = SecurityUtils.generateToken("test");
		String username = "username";
		assertNotNull(token);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/user").header("Authorization", token).param("username", username))
				.andDo(print());
	}
	
}