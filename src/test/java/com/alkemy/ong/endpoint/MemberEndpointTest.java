package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.MemberController;
import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import java.util.Locale;
import java.util.Optional;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IMemberService service;
    @MockBean
    private MemberRepository repository;
    @Autowired
    private MemberController controller;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    MemberMapper categoryMapper;
    @Autowired
    private ObjectMapper mapper;

    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;

    MemberDTO memberOne = new MemberDTO();
    MemberDTO memberTwo = new MemberDTO();

    @BeforeEach
    void setUp() throws Exception {
        LoginDto bodyAdmin = new LoginDto("diana.guzman@gmail.com", "123456789");
        LoginDto bodyUser = new LoginDto("adriana.hernandez@gmail.com", "123456789");

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

        tokenAdmin = mapper.readValue(responseAdmin, AuthenticationResponse.class);
        tokenUser = mapper.readValue(responseUser, AuthenticationResponse.class);

        memberOne.setName("memberOne");
        memberOne.setDescription("descriptionOne");
        memberOne.setImage("imageOne.jpg");
        memberOne.setInstagramUrl("igUrl.com");
        memberOne.setFacebookUrl("facebookUrl.com");
        memberOne.setLinkedinUrl("linkedinUrl.com");

        memberTwo.setName("memberTwo");
        memberTwo.setDescription("descriptionTwo");
        memberTwo.setImage("imageTwo.jpg");
        memberTwo.setInstagramUrl("igUrl.com");
        memberTwo.setFacebookUrl("facebookUrl.com");
        memberTwo.setLinkedinUrl("linkedinUrl.com");
    }

    @Test
    @Order(1)
    @DisplayName("Add member: If the user has role 'ADMIN' method will save the member and return status 201")
    void endPointCreateAsAdmin() throws Exception {
        mockMvc.perform(post("/members")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberOne)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("Add member: If the user has role 'USER' method will save the member and return status 201")
    void endPointCreateAsUser() throws Exception {
        mockMvc.perform(post("/members")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberTwo)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(3)
    @DisplayName("Add member: If the user hasn't been logged, will not save the category and return status 401")
    void endPointCreateWithoutAuth() throws Exception {
        mockMvc.perform(post("/members")
                        .accept(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    @DisplayName("Add member: If the request isn't valid, method return status 400")
    void endPointCreateReturnBadRequest()throws Exception{
        mockMvc.perform(post("/members")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    @DisplayName("List members: If the user has role 'ADMIN' method return the list of members and status 200")
    void endPointlistAsAdmin()throws Exception{
        mockMvc.perform(get("/members/?page=0")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("List members: If the user has role 'USER' method will not show the list and return status 403")
    void endPointlistAsUser()throws Exception{
        mockMvc.perform(get("/members/?page=0")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(7)
    @DisplayName("List members: If the user hasn't been logged, method will not show the list and return status 401")
    void endPointlistWithoutAuth()throws Exception{
        mockMvc.perform(get("/members/?page=0")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    @DisplayName("List members: If the user has role 'ADMIN' but the list is empty method return an exception and status 200")
    void endPointlistIsNull()throws Exception{
        when(service.returnList(0)).thenThrow(new NullListException(messageSource.getMessage("error.null_list", null, Locale.US)));
        mockMvc.perform(get("/members/?page=0")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    @DisplayName("Delete member: If the user has role 'ADMIN' method will delete the member and return status 200")
    void endPointdeleteAsAdmin()throws Exception{
        mockMvc.perform(delete("/members/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    @DisplayName("Delete members: If the user has role 'USER' method will not delete the member and return status 403")
    void endPointdeleteAsUser()throws Exception{
        mockMvc.perform(delete("/members/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(11)
    @DisplayName("Delete members: If the user hasn't been logged, method will not delete the member and return status 401")
    void endPointdeleteWithoutAuth()throws Exception{
        mockMvc.perform(delete("/members/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(12)
    @DisplayName("Delete member: If the id doesn't exist method will return 404")
    void endPointdeleteIDNotValid() throws Exception{
        Long idNotExist = 10L;

        when(controller.delete(idNotExist)).thenThrow(new EntityNotFoundException("Members", "id", idNotExist));

        mockMvc.perform(delete("/members/{id}", idNotExist)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    @DisplayName("Update member: If the user has role 'ADMIN' method update the member and return status 200")
    void endPointUpdateAsAdmin() throws Exception {
        mockMvc.perform(put("/members/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberOne)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(14)
    @DisplayName("Update member: If the user has role 'USER' method will update the member and return status 200")
    void endPointUpdateAsUser() throws Exception {
        mockMvc.perform(put("/members/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(memberTwo)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    @DisplayName("Update member: If the user hasn't been logged, method will not update the member and return status 401")
    void endPointUpdateWithoutAuth() throws Exception {
        mockMvc.perform(put("/members/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(16)
    @DisplayName("Update member: If the request isn't valid, method return status 400")
    void endPointUpdateReturnBadRequest()throws Exception{
                mockMvc.perform(put("/members/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
}

