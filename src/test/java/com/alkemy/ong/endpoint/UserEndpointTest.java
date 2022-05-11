package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.dto.UserPatchDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class UserEndpointTest {

	@Autowired
	private MockMvc mockMvc;
	private String tokenAdmin;
	private String tokenUser;

	@BeforeEach
	void setUp() throws Exception {
		LoginDto bodyAdmin = new LoginDto("diego.torres@gmail.com", "123456789");
		LoginDto bodyUser = new LoginDto("alejandro.sanchez@gmail.com", "123456789");

		tokenAdmin = mockMvc.perform(post("/auth/login")
						.content(asJsonString(bodyAdmin))
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		tokenUser = mockMvc.perform(post("/auth/login")
						.content(asJsonString(bodyUser))
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andReturn().getResponse().getContentAsString();

		assertNotNull(tokenAdmin);
		assertNotNull(tokenUser);
	}

	@Test
	@Order(1)
	void getAllUsersAuthenticatedAsAdmin() throws Exception {
		final ResultActions result = mockMvc.perform(get("/users")
						.header("Authorization", "Bearer " + tokenAdmin)
						.content(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andDo(print());
		String contentType = MediaType.APPLICATION_JSON_VALUE;
		final int expectedSize = 20;
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.length()").value(expectedSize));
		result.andExpect(content().contentType(contentType));

	}

	@Test
	@Order(2)
	void getAllUsersAuthenticatedAsUser() throws Exception {
		final ResultActions result = mockMvc.perform(get("/users")
						.header("Authorization", "Bearer " + tokenUser)
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isForbidden());
	}

	@Test
	@Order(3)
	void getAllUsersWithoutAuthentication() throws Exception {
		final ResultActions result = mockMvc.perform(post("/users")
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isUnauthorized());
	}

	@Test
	@Order(4)
	void deleteUserByIdAuthenticatedAsAdmin() throws Exception {
		final ResultActions result = mockMvc.perform(delete("/users/{id}", 1)
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isOk());

		final ResultActions resultDeleted = mockMvc.perform(get("/users")
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		int expectedSize = 19;
		resultDeleted.andExpect(status().isOk());
		resultDeleted.andExpect(jsonPath("$.length()").value(expectedSize));
	}

	@Test
	@Order(5)
	void deleteUserByIdAuthenticatedAsUser() throws Exception {
		final ResultActions result = mockMvc.perform(delete("/users/{id}", 1)
						.header("Authorization", "Bearer " + tokenUser)
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isForbidden());
	}

	@Test
	@Order(6)
	void deleteUserByIdWithoutAuthentication() throws Exception {
		final ResultActions result = mockMvc.perform(delete("/users/{id}", 1)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isUnauthorized());
	}

	@Test
	@Order(7)
	void deleteUserByIdWithWrongId() throws Exception {
		final ResultActions result = mockMvc.perform(delete("/users/{id}", 0)
				.header("Authorization", "Bearer " + tokenAdmin)
				.content(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isNotFound());
	}

	@Test
	@Order(8)
	void patchUserByIdAuthenticatedAsAdmin() throws Exception {
		final ResultActions users = mockMvc.perform(get("/users")
						.header("Authorization", "Bearer " + tokenAdmin)
						.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
						.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andDo(print());

		final ResultActions result = mockMvc.perform(patch("/users/{id}", 2)
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(new UserPatchDto("Lucas", "Bravi", "MyPhoto")))
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.firstName").value("Lucas"));
		result.andExpect(jsonPath("$.lastName").value("Bravi"));
		result.andExpect(jsonPath("$.photo").value("MyPhoto"));
	}

	@Test
	@Order(9)
	void patchUserByIdAuthenticatedAsUser() throws Exception {
		final ResultActions result = mockMvc.perform(patch("/users/{id}", 2)
				.header("Authorization", "Bearer " + tokenUser)
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(new UserPatchDto("Lucas", "Bravi", "MyPhoto")))
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isForbidden());
	}

	@Test
	@Order(10)
	void patchUserByIdWithoutAuthentication() throws Exception {
		final ResultActions result = mockMvc.perform(patch("/users/{id}", 2)
				.content(asJsonString(new UserPatchDto("Lucas", "Bravi", "MyPhoto")))
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isUnauthorized());
	}


	@Test
	@Order(11)
	void patchUserByIdWithWrongId() throws Exception {
		final ResultActions result = mockMvc.perform(patch("/users/{id}", 0)
				.header("Authorization", "Bearer " + tokenAdmin)
				.content(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(new UserPatchDto("Lucas", "Bravi", "MyPhoto")))
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isNotFound());
	}
	@Test
	@Order(12)
	void patchUserByIdWithEmptyBody() throws Exception {
		final ResultActions result = mockMvc.perform(patch("/users/{id}", 2)
				.header("Authorization", "Bearer " + tokenAdmin)
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.content(asJsonString(new UserPatchDto()))
				.contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

		result.andExpect(status().isBadRequest());
	}



}
