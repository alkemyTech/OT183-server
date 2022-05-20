package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.service.IActivityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActivityEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private IActivityService service;

    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;

    ActivityDto activity1 = new ActivityDto();
    ActivityDto activity2 = new ActivityDto();

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

        activity1.setName("nameActivity1");
        activity1.setContent("contentActivity1");
        activity1.setImage("imageActivity1");


    }

    @Test
    @Order(1)
    @DisplayName("Add activity: if the user is an admin")
    void testCreateActivityAsAdmin() throws Exception {
        when(service.createActivity(activity1)).thenReturn(activity1);

        mockMvc.perform(post("/activities")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(activity1)))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(2)
    @DisplayName("user not authorized to add activity")
    void testCreateActivityAsUser() throws Exception {
        mockMvc.perform(post("/activities")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(activity1)))
                .andExpect(status().isForbidden());

    }


    @Test
    @Order(3)
    @DisplayName("Update activity if user is an admin")
    void testUpdateActivityAsAdmin() throws Exception {
        when(service.updateActivity(20L, activity1)).thenReturn(activity1);

        mockMvc.perform(put("/activities/{id}", 20L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(activity1)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateActivity(anyLong(), any());
    }

    @Test
    @Order(4)
    @DisplayName("Update Activity: return not found if not exist id of activity")
    void testUpdateActivityNotFound() throws Exception {
        when(service.updateActivity(20L, activity1)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(put("/activities/{id}", 20L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(activity1)))
                .andExpect(status().isNotFound());
        verify(service, times(1)).updateActivity(anyLong(), any());
    }

    @Test
    @Order(5)
    @DisplayName("don't update activity if user is not an admin")
    void testUpdateActivityAsUser() throws Exception {
        mockMvc.perform(put("/activities/{id}", 20L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(activity1)))
                .andExpect(status().isForbidden());
        verify(service, times(0)).updateActivity(anyLong(), any());
    }


}

