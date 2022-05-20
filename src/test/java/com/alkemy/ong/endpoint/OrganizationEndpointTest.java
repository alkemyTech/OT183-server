package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.OrganizationController;
import com.alkemy.ong.dto.OrganizationDetailedDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrganizationEndpointTest {
    @Autowired
	private MockMvc mockMvc;
    private AuthenticationResponse tokenAdmin;
	private AuthenticationResponse tokenUser;
    private OrganizationDetailedDto organizationUpdate;
	@Autowired
	ObjectMapper objectMapper;
	@MockBean
    private OrganizationController organizationController;
    
    @BeforeEach
	void setUp() throws Exception {
		LoginDto bodyAdmin = new LoginDto("diego.torres@gmail.com", "123456789");
		LoginDto bodyUser = new LoginDto("alejandro.sanchez@gmail.com", "123456789");

		String responseAdmin = mockMvc.perform(post("/auth/login")
						.content(asJsonString(bodyAdmin))
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		String responseUser = mockMvc.perform(post("/auth/login")
						.content(asJsonString(bodyUser))
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		tokenAdmin = objectMapper.readValue(responseAdmin, AuthenticationResponse.class);
		tokenUser = objectMapper.readValue(responseUser, AuthenticationResponse.class);
        organizationUpdate = new OrganizationDetailedDto("ONG Name","PhotoONG", 
        "address","342123",
        "email@gmail.com",
        "Welcome text",
        "AboutUsText",
        "facebook-page",
        "instagram-page",
        "linkedin-page");
        
        assertNotNull(organizationUpdate);
		assertNotNull(tokenAdmin);
		assertNotNull(tokenUser.getJwt());
	}

    @Test
	@Order(1)
	void getOngInformationAuthenticatedAsAdmin() throws Exception {
		final ResultActions result = mockMvc.perform(get("/organization/public")
						.header("Authorization", "Bearer " + tokenAdmin.getJwt())
						.content(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andDo(print());
		result.andExpect(status().isOk());
	}

    @Test
	@Order(2)
	void getOngInformationAuthenticatedAsClient() throws Exception {
		final ResultActions result = mockMvc.perform(get("/organization/public")
						.header("Authorization", "Bearer " + tokenUser.getJwt())
						.content(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andDo(print());
		result.andExpect(status().isOk());
	}

    @Test
	@Order(3)
	void patchOngInformationAuthenticatedAsAdmin() throws Exception {
				
		Mockito.when(organizationController.update(organizationUpdate))
		.thenReturn(ResponseEntity.status(HttpStatus.OK).build());
        
		final ResultActions result = mockMvc.perform(put("/organization/public")
			.header("Authorization", "Bearer " + tokenAdmin.getJwt())
			.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
			.content(asJsonString(organizationUpdate))
			.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
			.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void patchOngByWithEmptyBody() throws Exception {

		Mockito.when(organizationController.update(new OrganizationDetailedDto()))
		.thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

		final ResultActions result = mockMvc.perform(put("/organization/public")
		.header("Authorization", "Bearer " + tokenAdmin.getJwt())
		.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
		.content(asJsonString(new OrganizationDetailedDto()))
		.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
		.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isBadRequest());

	}



}
