package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.NameUrlDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.ICategoryService;
import com.alkemy.ong.util.PaginationUtil;
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
import java.util.Locale;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    @Order(5)
    @DisplayName("List category: If the user has role 'ADMIN' method return the list of categories and status 200")
    void endPointlistCategoryAsAdmin()throws Exception{

        NameUrlDto url = categoryMapper.listNameDto(listCategoryResponse, PaginationUtil.getPreviousAndNextPage(0, 1));

        when(service.returnList(0)).thenReturn(url);
        mockMvc.perform(get("/categories/?page=0")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("List category: If the user has role 'USER' method will not show the list and return status 403")
    void endPointlistCategoryAsUser()throws Exception{

        mockMvc.perform(get("/categories/?page=0")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(7)
    @DisplayName("List category: If the user hasn't been logged, method will not show the list and return status 401")
    void endPointlistCategoryWithoutAuth()throws Exception{
        mockMvc.perform(get("/categories/?page=0")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(8)
    @DisplayName("List category: If the user has role 'ADMIN' but the list is empty method return an exception and status 200")
    void endPointlistCategoryIsNull()throws Exception{
        when(service.returnList(any())).thenThrow(new NullListException(messageSource.getMessage("error.null_list", null, Locale.US)));
        mockMvc.perform(get("/categories/?page=0")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    @DisplayName("Delete category: If the user has role 'ADMIN' method will delete the category and return status 200")
    void endPointdeleteCategoryAsAdmin()throws Exception{
        mockMvc.perform(delete("/categories/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    @DisplayName("Delete category: If the user has role 'USER' method will not delete the category and return status 403")
    void endPointdeleteCategoryAsUser()throws Exception{

        mockMvc.perform(delete("/categories/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(11)
    @DisplayName("Delete category: If the user hasn't been logged, method will not delete the category and return status 401")
    void endPointdeleteWithoutAuth()throws Exception{

        mockMvc.perform(delete("/categories/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(12)
    @DisplayName("Delete Category: If the id doesn't exist method will return 404")
    void endPointdeleteCategoryIDNotValid() throws Exception{
        Long idNotExist = 10L;

        when(controller.delete(idNotExist)).thenThrow(new EntityNotFoundException("Category", "id", idNotExist));

        mockMvc.perform(delete("/categories/{id}", idNotExist)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    @DisplayName("Update category: If the user has role 'ADMIN' method update the category and return status 200")
    void endPointUpdateAsAdmin() throws Exception {
        mockMvc.perform(put("/categories/{id}", 3L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryThree)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(14)
    @DisplayName("Update category: If the user has role 'USER' method will not update the category and return status 403")
    void endPointUpdateAsUser() throws Exception {
        mockMvc.perform(put("/categories/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryThree)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(15)
    @DisplayName("Update category: If the user hasn't been logged, method will not update the category and return status 401")
    void endPointUpdateWithoutAuth() throws Exception {
        mockMvc.perform(put("/categories/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(16)
    @DisplayName("Update category: If the request isn't valid, method return status 400")
    void endPointUpdateReturnBadRequest()throws Exception{
        when(controller.updateCategory(null, 3L)).thenReturn(null);

        mockMvc.perform(put("/categories/{id}", 3L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(17)
    @DisplayName("Update category: If the id doesn't exist method will return 400")
    void endPointUpdateIDNotValid()throws Exception{
        Long idNotExist = 10L;

        when(service.updateCategory(categoryThree, idNotExist)).thenThrow(new ParamNotFound(messageSource.getMessage("error.id",null, Locale.US)));

        mockMvc.perform(put("/categories/{id}", idNotExist)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(categoryThree)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(18)
    @DisplayName("Get category by ID: If the user has role 'ADMIN' method will return the category with status 200")
    void endPointGetByIDAsAdmin() throws Exception {
        when(service.getById(1L)).thenReturn(categoryOne);

        mockMvc.perform(get("/categories/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(19)
    @DisplayName("Get category by ID: If the user has role 'USER' method will not show the category and return status 403")
    void endPointGetByIDAsUser() throws Exception {
        mockMvc.perform(get("/categories/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(20)
    @DisplayName("Get category by ID: If the user hasn't been logged, method will not show the category and return status 401")
    void endPointGetByIDWithoutAuth() throws Exception {
        mockMvc.perform(get("/categories/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(21)
    @DisplayName("Get category by ID: If the id doesn't exist method will return 404")
    void endPointGetByIDIDNotValid()throws Exception{
        Long idNotExist = 10L;

       when(service.getById(idNotExist)).thenThrow(new EntityNotFoundException("Category", "id", idNotExist));

        mockMvc.perform(get("/categories/{id}", idNotExist)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
