package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.CategoryController;
import com.alkemy.ong.controller.TestimonialController;
import com.alkemy.ong.dto.PaginationUrlDto;
import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.dto.type.TestimonialDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import com.alkemy.ong.util.pagination.Pagination;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.pool.TypePool;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.MimeTypeUtils;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestimonialEndpointTest {

    @MockBean
    private TestimonialRepository repository;
    @MockBean
    private TestimonialServiceImpl service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private TestimonialController controller;

    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;

    private Testimonial testimonial = new Testimonial();
    private Pagination pagination = new Pagination();

    TestimonialDto dtoRequest;
    Object dtoResponseObject;


    @BeforeEach
    void setUp() throws Exception {
         dtoRequest = TestimonialDto.builder()
                .name("testimonial1")
                .content("content testimonial 1")
                .build();

        TestimonialDto dtoResponse = TestimonialDto.builder()
                .id(1L)
                .name("testimonial1")
                .content("content testimonial 1")
                .build();

        dtoResponseObject = new Object();
        dtoResponseObject = dtoResponse;

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
        testimonial.setId(1L);
        testimonial.setName("matias");
        pagination.setPages(10);
        pagination.setCurrentPage(0);
        pagination.setUrls(new PaginationUrlDto("prev","next"));
        Pageable pageable = PageRequest.of(0,10);




    }

    @Test
    @Order(1)
    @DisplayName("Get /testimonials - isOk")
    public void getAllAuthOk() throws Exception {

        when(service.getAllTestimonialPaged(any(Pageable.class),any(Integer.class))).thenReturn(pagination);
        mockMvc.perform(get("/testimonials")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("GET /testimonials - isForbidden")
    public void getAllAuthForbiden() throws Exception {

        when(service.getAllTestimonialPaged(any(Pageable.class),any(Integer.class))).thenReturn(pagination);
        mockMvc.perform(get("/testimonials")
                        .header("Authorization", "Bearer " + tokenUser.getJwt())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
    @Test
    @Order(3)
    @DisplayName("GET /testimonials - isUnauthorized")
    public void getAllAuthUnauthorized() throws Exception {

        when(service.getAllTestimonialPaged(any(Pageable.class),any(Integer.class))).thenReturn(pagination);
        mockMvc.perform(get("/testimonials"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(4)
    @DisplayName("POST /testimonials - isForbidden")
    public void postAuthForbiden() throws Exception{

        when(service.createTestimonial(any(TestimonialDto.class))).thenReturn(dtoResponseObject);

        mockMvc.perform(post("/testimonials")
                     .header("Authorization", "Bearer " + tokenUser.getJwt())
                     .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                     .content(asJsonString(dtoRequest)))
                .andExpect(status().isForbidden());
    }
    @Test
    @Order(5)
    @DisplayName("POST testimonials - isUnauthorized")
    public void postAuthUnauthorized() throws Exception{

        when(service.createTestimonial(any(TestimonialDto.class))).thenReturn(dtoResponseObject);
        mockMvc.perform(post("/testimonials")
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(asJsonString(dtoRequest)))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @Order(6)
    @DisplayName("POST /testimonials - isBadRquest")
    public void postBadRequest() throws Exception{

        when(service.createTestimonial(any(TestimonialDto.class))).thenReturn(dtoResponseObject);
        mockMvc.perform(post("/testimonials")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());
    }

    //DEVUELVE 200 EN VEZ DE 201
    @Test
    @Order(7)
    @DisplayName("POST /testimonials - isCreated")
    public void postSave() throws Exception{

        when(service.createTestimonial(any(TestimonialDto.class))).thenReturn(dtoResponseObject);
        mockMvc.perform(post("/testimonials")
                     .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                     .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE)
                     .content(objectMapper.writeValueAsString(dtoRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(8)
    @DisplayName("DELETE /testimonials/{id} - isUnauthorized")
    public void deleteAuthUnathorized() throws Exception{
        mockMvc.perform(delete("/testimonials/{id}",1L)).andExpect(status().isUnauthorized());
    }

    @Test
    @Order(9)
    @DisplayName("DELETE /testimonials/{id} - isForbidden")
    public void deleteAuthForbiden() throws Exception{

        mockMvc.perform(delete("/testimonials/{id}",1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt()))
                .andExpect(status().isForbidden());
    }

    //El controller no deberia devolver NotContent  como respuesta ante un delete?
    @Test
    @Order(10)
    @DisplayName("DELETE /testimoanials{id} - isOk")
    void deleteOk() throws Exception{
        mockMvc.perform(delete("/testimonials/{id}", 1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @Order(11)
    @DisplayName("DELETE /testimonials/{id} - isBadRequest")
    void deleteBadRequest() throws Exception{
        mockMvc.perform(delete("/testimonials/{id}", "1L")
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Tiene sentido evaluar el mensaje de error? vi el codigo de un compañero y lo quise implementar, pero no seria un test de service?
    @Test
    @Order(12)
    @DisplayName("DELETE /testimonials{id} - isNotFound")
    void deleteIDNotValid() throws Exception{
        Long idNotExist = 1L;
        when(controller.deleteTestimonial(idNotExist)).thenThrow(new EntityNotFoundException("Testimonial", "id", idNotExist));

        mockMvc.perform(delete("/testimonials/{id}", idNotExist)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    @DisplayName("PUT /testimonials{id} - isUnauthorized")
    public void putAuthUnauthorized() throws Exception{
        mockMvc.perform(put("/testimonials/{id}",1L))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(14)
    @DisplayName("PUT /testimonials{id} - isOk")
    public void putOk() throws Exception{

        when(service.updateTestimonialById(any(Long.class),any(TestimonialDto.class))).thenReturn(dtoResponseObject);
        mockMvc.perform(put("/testimonials/{id}",1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(asJsonString(dtoRequest)))

                .andExpect(status().isOk());
    }

    @Test
    @Order(15)
    @DisplayName("PUT /testimonials/{id} - isBadRequest")
    public void putBadRequest() throws Exception{

        when(service.updateTestimonialById(any(Long.class),any(TestimonialDto.class))).thenReturn(dtoResponseObject);
        mockMvc.perform(put("/testimonials/{id}",1L)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(asJsonString(null)))

                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(16)
    @DisplayName("PUT /testimonials/{id} - isForbidden")
    public void putAuthForbiden() throws Exception{
        mockMvc.perform(put("/testimonials/{id}",1L)
                        .header("Authorization", "Bearer " + tokenUser.getJwt()))
                .andExpect(status().isForbidden());
    }


}