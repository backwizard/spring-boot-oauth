//package com.example.security;
//
//import static org.junit.Assert.assertNotNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import javax.servlet.FilterChain;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.security.web.FilterChainProxy;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//// @AutoConfigureMockMvc
//public class JWTAuthorizationFilterTest {
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	// @Autowired
//	// private MockMvc mockMvc;
//
//	// @Autowired
//	// private FilterChainProxy springSecurityFilterChain;
//
//	private MockMvc mockMvc;
//
//	@Mock
//	HttpServletRequest request;
//
//	@Mock
//	HttpServletResponse response;
//
//	@Mock
//	FilterChain chain;
//
//	@Autowired
//	private FilterChainProxy filterChainProxy;
//
//	@Before
//	public void setUp() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(SecurityMockMvcConfigurers.springSecurity())
//				.addFilter(filterChainProxy).build();
//	}
//
//	@Test
//	public void givenNoToken_whenGetSecureRequest_thenAccessDenied() throws Exception {
//		String username = "username";
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user").param("username", username)).andDo(print())
//				.andExpect(status().is(403));
//	}
//
//	@Test
//	public void shouldGenerateAuthToken() throws Exception {
//		String token = SecurityUtils.generateToken("test");
//		String username = "username";
//		assertNotNull(token);
//		mockMvc.perform(
//				MockMvcRequestBuilders.get("/api/v1/user").header("Authorization", token).param("username", username))
//				.andDo(print()).andExpect(status().isOk());
//
//	}
//}
