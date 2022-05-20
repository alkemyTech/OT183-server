package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.NewsController;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.service.INewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.alkemy.ong.util.TestUtil.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsEndpointTest {

    @MockBean
    private INewsService service;
    @MockBean
    private NewsController controller;
    @Autowired
    private MockMvc mockMvc;
    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;
    private MessageSource messageSource;

    @Autowired
    ObjectMapper objectMapper;


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

        assertNotNull(tokenAdmin);
        assertNotNull(tokenUser.getJwt());
    }


    @Test
    @Order(1)
    void newsShouldCreated()throws Exception{

        NewsDto newsDto = new NewsDto();

        newsDto.setName("Name");
        newsDto.setContent("Content");
        newsDto.setImage("image");
        newsDto.setCategoryId(1L);

        final ResultActions result = mockMvc.perform(post("/news")
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .content(asJsonString(newsDto))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
        /*
        result.andExpect(jsonPath("$.name").value("Name"));
        result.andExpect(jsonPath("$.content").value("Content"));
        result.andExpect(jsonPath("$.image").value("image"));
        result.andExpect(jsonPath("$.categoryId").value(1));*/
    }

    @Test
    @Order(2)
    void newsShouldNotCreateWithoutAdminRole() throws Exception{

        NewsDto newsDto = new NewsDto();

        newsDto.setName("Name");
        newsDto.setContent("Content");
        newsDto.setImage("image");
        newsDto.setCategoryId(1L);

        final ResultActions result = mockMvc.perform(post("/news")
                .header("Authorization", String.format("Bearer %s", tokenUser.getJwt()))
                .content(asJsonString(newsDto))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());

    }

    @Test
    @Order(3)
    void newsShouldNotCreateWithoutAuthentication()throws Exception{

        NewsDto newsDto = new NewsDto();

        newsDto.setName("Name");
        newsDto.setContent("Content");
        newsDto.setImage("image");
        newsDto.setCategoryId(1L);

        final ResultActions result = mockMvc.perform(post("/news")
                .content(asJsonString(newsDto))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    void newsShouldDelete()throws Exception{

        Mockito.when(controller.delete(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        final ResultActions result = mockMvc.perform(delete("/news/{id}",1)
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());

    }

    @Test
    @Order(5)
    void newsShouldNotDeleteByInvalidId()throws Exception{

        Long invalidId = 0L;

        ResponseEntity responseNotFound =  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id");


        Mockito.when(controller.delete(invalidId)).thenReturn(responseNotFound);

        final ResultActions result = mockMvc.perform(delete("/news/{id}",invalidId)
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isNotFound());

    }

    @Test
    @Order(6)
    void newsShouldNotDeleteWithoutAuthentication()throws Exception{

        Mockito.when(controller.delete(1L)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        final ResultActions result = mockMvc.perform(delete("/news/{id}",1)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());

    }

    @Test
    @Order(7)
    void newsShouldNotDeleteWithoutAdminRole()throws Exception{

        final ResultActions result = mockMvc.perform(delete("/news")
                .header("Authorization", String.format("Bearer %s", tokenUser.getJwt()))
                .content(asJsonString(1L))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());

    }


    @Test
    @Order(8)
    void newsShouldUpdate()throws Exception{

        NewsUpdateDTO update = new NewsUpdateDTO("name","content","image",1L);

        Mockito.when(controller.updateNews(1L,update)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        final ResultActions result = mockMvc.perform(patch("/news/{id}",1)
                .header("Authorization",String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(asJsonString(update))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

       result.andExpect(status().isOk());

    }

    @Test
    @Order(9)
    void newsShouldNotUpdateByInvalidId()throws Exception{

        NewsUpdateDTO update = new NewsUpdateDTO("name","content","image",1L);

        Long invalidId = 0L;

        ResponseEntity responseNotFound =  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id");


        Mockito.when(controller.updateNews(invalidId,update)).thenReturn(responseNotFound);

        final ResultActions result = mockMvc.perform(patch("/news/{id}",invalidId)
                .header("Authorization",String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(asJsonString(update))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isNotFound());

    }

    @Test
    @Order(10)
    void newsShoulNotdUpdateWithoutAuthentication()throws Exception{

        NewsUpdateDTO update = new NewsUpdateDTO("name","content","image",1L);

        Mockito.when(controller.updateNews(1L,update)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        final ResultActions result = mockMvc.perform(patch("/news/{id}",1)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(asJsonString(update))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());

    }


    @Test
    @Order(11)
    void newsShouldNotUpdateWithoutAdminRole()throws Exception{

        NewsUpdateDTO update = new NewsUpdateDTO("name","content","image",1L);

        Mockito.when(controller.updateNews(1L,update)).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

        final ResultActions result = mockMvc.perform(patch("/news/{id}",1)
                .header("Authorization",String.format("Bearer %s", tokenUser.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .content(asJsonString(update))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());

    }

    @Test
    @Order(12)
    void newsShouldGetById()throws Exception{


        final ResultActions result = mockMvc.perform(get("/news/{id}",1)
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());

    }

    @Test
    @Order(13)
    void newsShouldNotGetByInvalidId()throws Exception{

        Long invalidId = 0L;

        ResponseEntity responseNotFound =  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid id");

        Mockito.when(controller.getNewsById(invalidId)).thenReturn(responseNotFound);

        final ResultActions result = mockMvc.perform(get("/news/{id}",invalidId)
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isNotFound());

    }

    @Test
    @Order(14)
    void newsShouldNotGetByIdWithoutAuthentication()throws Exception{

        final ResultActions result = mockMvc.perform(get("/news/{id}",1)
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());

    }

    @Test
    @Order(15)
    void newsShouldNotGetByIdWithoutAdminRole()throws Exception{

        final ResultActions result = mockMvc.perform(get("/news/{id}",1)
                .header("Authorization", String.format("Bearer %s", tokenUser.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());

    }

    @Test
    @Order(16)
    void newsShouldGetAll()throws Exception{

        final ResultActions result = mockMvc.perform(get("/news")
                .header("Authorization", String.format("Bearer %s", tokenAdmin.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isOk());
    }

    @Test
    @Order(17)
    void newsShoulNotdGetAllWithoutAuthentication()throws Exception{

        final ResultActions result = mockMvc.perform(get("/news")
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    @Order(18)
    void newsShouldNotGetAllWithoutAdminRole()throws Exception{

        final ResultActions result = mockMvc.perform(get("/news")
                .header("Authorization", String.format("Bearer %s", tokenUser.getJwt()))
                .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                .accept(MimeTypeUtils.APPLICATION_JSON_VALUE));

        result.andExpect(status().isForbidden());
    }


}
