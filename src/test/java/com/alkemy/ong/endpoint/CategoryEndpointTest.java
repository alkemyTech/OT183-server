package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryEndpointTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ICategoryService service;
    @MockBean
    private CategoryRepository repository;
    @Autowired
    private CategoryController controller;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    private ObjectMapper mapper;

    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;

    CategoryDto categoryOne = new CategoryDto();
    CategoryDto categoryTwo = new CategoryDto();
    CategoryDto categoryThree = new CategoryDto();
    List<Category> listCategoryResponse = new ArrayList<>();

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

        categoryOne.setName("CategoryOne");
        categoryOne.setDescription("descriptionOne");
        categoryOne.setImage("imageOne.jpg");

        categoryTwo.setName("CategoryTwo");
        categoryTwo.setDescription("descriptionTwo");
        categoryTwo.setImage("imageTwo.jpg");

        categoryThree.setName("CategoryThree");
        categoryThree.setDescription("descriptionThree");
        categoryThree.setImage("imageThree.jpg");

        listCategoryResponse.add(categoryMapper.toEntity(categoryOne));
        listCategoryResponse.add(categoryMapper.toEntity(categoryTwo));
        listCategoryResponse.add(categoryMapper.toEntity(categoryThree));
    }

    @Test
    @Order(1)
    @DisplayName("Add category: If the user has role 'ADMIN' method save a new category and return status 201")
    void endPointCreateAsAdmin() throws Exception {
        when(service.addCategory(categoryOne)).thenReturn(categoryOne);

        mockMvc.perform(post("/categories")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryOne)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    @DisplayName("Add category: If the user has role 'USER' will not save the category and return status 403")
    void endPointCreateAsUser() throws Exception {
        when(service.addCategory(categoryOne)).thenReturn(categoryOne);

        mockMvc.perform(post("/categories")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryOne)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    @DisplayName("Add category: If the user hasn't been logged, will not save the category and return status 401")
    void endPointCreateWithoutAuth() throws Exception {
        mockMvc.perform(post("/categories")
                        .accept(APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    @DisplayName("Add category: If the request isn't valid, method return status 400")
    void endPointCreateReturnBadRequest()throws Exception{
        when(service.addCategory(null)).thenReturn(null);

        mockMvc.perform(post("/categories")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }


}
