package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.auth.dto.UserDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MimeTypeUtils;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationEndpointTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    void userShouldLogIn() throws Exception {
        final ResultActions result = mockMvc.perform(post("/auth/login")
                        .content(
                                asJsonString(
                                        new LoginDto("adriana.hernandez@gmail.com", "123456789")
                                )
                        )
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.jwt", is(not(empty()))));
    }

    @Test
    @Order(2)
    void userShouldNotLogin() throws Exception {
        final ResultActions result = mockMvc.perform(post("/auth/login")
                .content(
                        asJsonString(
                                new LoginDto("adriana_hernandez@gmail.com", "1234")
                        )
                )
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    @Order(3)
    void userShouldRegister() throws Exception {
        final ResultActions result = mockMvc.perform(post("/auth/register")
                .content(asJsonString(UserDto.builder()
                        .email("lucas@gmail.com")
                        .firstName("lucas")
                        .lastName("bravi")
                        .password(passwordEncoder.encode("1234"))
                        .photo("myphoto")
                        .build())
                )
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.firstName", is("lucas")));
        result.andExpect(jsonPath("$.lastName", is("bravi")));
        result.andExpect(jsonPath("$.email", is("lucas@gmail.com")));
    }

    @Test
    @Order(4)
    void userShouldNotRegister() throws Exception {
        final ResultActions result = mockMvc.perform(post("/auth/register")
                .content(asJsonString(UserDto.builder()
                        .email("lucas@gmail.com")
                        .firstName("lucas")
                        .lastName("bravi")
                        .photo("myphoto")
                        .build())
                )
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void userShouldNotGetProfileWithoutAuthentication() throws Exception {
        final ResultActions result = mockMvc.perform(get("/auth/me")
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    @Order(6)
    void userShouldNotGetProfileWithoutAdminRole() throws Exception {

        LoginDto bodyUser = new LoginDto("alejandro.sanchez@gmail.com", "123456789");
        String responseUser = mockMvc.perform(post("/auth/login")
                        .content(asJsonString(bodyUser))
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        AuthenticationResponse tokenUser = objectMapper.readValue(responseUser, AuthenticationResponse.class);

        final ResultActions result = mockMvc.perform(get("/auth/me")
                        .header("Authorization", String.format("Bearer %s", tokenUser.getJwt()))
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());
    }

    @Test
    @Order(7)
    void userShouldGetProfile() throws Exception {
        LoginDto bodyAdmin = new LoginDto("diego.torres@gmail.com", "123456789");
        String responseAdmin = mockMvc.perform(post("/auth/login")
                        .content(asJsonString(bodyAdmin))
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        AuthenticationResponse tokenAdmin = objectMapper.readValue(responseAdmin, AuthenticationResponse.class);
        final ResultActions result = mockMvc.perform(get("/auth/me")
                        .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.firstName", is("Diego")));
        result.andExpect(jsonPath("$.lastName", is("Torres")));
        result.andExpect(jsonPath("$.photo", is("/assets/photos/user20")));
        result.andExpect(jsonPath("$.email", is("diego.torres@gmail.com")));

    }


}
