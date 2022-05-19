package com.alkemy.ong.endpoint;

import com.alkemy.ong.auth.dto.AuthenticationResponse;
import com.alkemy.ong.auth.dto.LoginDto;
import com.alkemy.ong.controller.ContactController;
import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.IContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.util.TestUtil.asJsonString;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactEndpointTest {
    private final static String URI = "/contacts";
    ContactDto contactOneDto;
    ContactDto contactTwoDto;
    ContactBasicDto contactBasicDtoOne;
    List<ContactBasicDto> contactBasicDtoList = new ArrayList<>();
    List<Contact> contactList = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ContactController contactController;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private IContactService contactService;
    @MockBean
    private ContactRepository contactRepository;
    private AuthenticationResponse tokenAdmin;
    private AuthenticationResponse tokenUser;

    @BeforeEach
    void setUp() throws Exception {

        LoginDto bodyAdmin = new LoginDto("diana.guzman@gmail.com", "123456789");
        LoginDto bodyUser = new LoginDto("adriana.hernandez@gmail.com", "123456789");

        String responseAdmin = mockMvc.perform(post("/auth/login").content(asJsonString(bodyAdmin)).contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();

        String responseUser = mockMvc.perform(post("/auth/login").content(asJsonString(bodyUser)).contentType(MimeTypeUtils.APPLICATION_JSON_VALUE).accept(MimeTypeUtils.APPLICATION_JSON_VALUE)).andReturn().getResponse().getContentAsString();

        tokenAdmin = objectMapper.readValue(responseAdmin, AuthenticationResponse.class);
        tokenUser = objectMapper.readValue(responseUser, AuthenticationResponse.class);

        contactOneDto = new ContactDto(1L, "Juan Perez", "+54261334455", "perezj@gmail.com", "Message 1");
        contactBasicDtoList = asList(new ContactBasicDto("Juan Perez", "perezj@gmail.com", "+54261334455", "Message 1", "2022/05/11"), new ContactBasicDto("Maria Paz", "pazm@gmail.com", "+54111334455", "Message 2", "2022/05/19"));
        contactList = asList(
                new Contact(1L, "Juan Perez", "+54261334455", "perezj@gmail.com", "Message 1", false, LocalDate.now()),
                new Contact(2L, "Maria Paz", "+54111334455", "pazm@gmail.com", "Message 2", false, LocalDate.now()));
    }

    @Test
    @Order(1)
    @DisplayName("Add contact: a new contact must be saved and return a 201 CREATED response code")
    void addContact_ShouldSaveContactAndReturnCreated() throws Exception {
        final ResultActions result = mockMvc.perform(post(URI).content(asJsonString(contactOneDto)).contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated())
                .andExpect(content().string("Your contact info was added correctly."))
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("Add contact with blank name: you must obtain a 400 response code with name error message")
    void addContact_BlankNameShouldReturnBadRequestResponse() throws Exception {
        mockMvc.perform(post(URI)
                        .content(asJsonString(ContactDto.builder()
                                .name("")
                                .phone("+5423455667")
                                .email("celsobrc@gmail.com")
                                .message("Message 2")
                                .build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("name: This is a mandatory field and can't be blank.")))
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("Add contact with blank email: you should obtain a 400 response code with email error message")
    void addContact_BlankEmailShouldReturnBadRequestResponse() throws Exception {
        mockMvc.perform(post(URI)
                        .content(asJsonString(ContactDto.builder()
                                .name("Celso Zabala")
                                .phone("+542334455")
                                .email("")
                                .message("Message 2")
                                .build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("email: This is a mandatory field and can't be blank.")))
                .andDo(print());
    }

    @Test
    @Order(4)
    @DisplayName("Add contact with invalid email: you should obtain a 400 response code with email error message")
    void addContact_InvalidEmailFormatShouldReturnBadRequestResponse() throws Exception {
        mockMvc.perform(post(URI)
                        .content(asJsonString(ContactDto.builder()
                                .name("Celso Zabala")
                                .phone("+542334455")
                                .email("celsobrc.com")
                                .message("Message 2")
                                .build())
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("email: Invalid email format.")))
                .andDo(print());
    }

    @Test
    @Order(5)
    @DisplayName("Get contacts: as Admin you should see all contacts list")
    void getContacts_AdminShouldSeeContactList() throws Exception {

        String expected = "[{\"name\":\"Contacto 4\",\"email\":\"contacto4@gmail.com\",\"phone\":\"+5491133445566\",\"message\":\"Mensaje 4\",\"created\":\"2022-05-17\"},{\"name\":\"Juan Perez\",\"email\":\"perezj@gmail.com\",\"phone\":\"+54261334455\",\"message\":\"Message 1\",\"created\":\"2022-05-18\"},{\"name\":\"Juan Perez\",\"email\":\"perezj@gmail.com\",\"phone\":\"+54261334455\",\"message\":\"Message 1\",\"created\":\"2022-05-18\"},{\"name\":\"Juan Perez\",\"email\":\"perezj@gmail.com\",\"phone\":\"+54261334455\",\"message\":\"Message 1\",\"created\":\"2022-05-18\"},{\"name\":\"Contacto 4\",\"email\":\"contacto4@gmail.com\",\"phone\":\"+5491133445566\",\"message\":\"Mensaje 4\",\"created\":\"2022-05-18\"},{\"name\":\"Juan Perez\",\"email\":\"perezj@gmail.com\",\"phone\":\"+54261334455\",\"message\":\"Message 1\",\"created\":\"2022-05-18\"},{\"name\":\"dasdas\",\"email\":\"contacto4@gmail.com\",\"phone\":\"\",\"message\":\"Mensaje 4\",\"created\":\"2022-05-18\"},{\"name\":\"dasdas\",\"email\":\"contacto4@gmail.com\",\"phone\":\"\",\"message\":\"Mensaje 4\",\"created\":\"2022-05-18\"},{\"name\":\"dasdas\",\"email\":\"contacto4@gmail.com\",\"phone\":\"+5491133445566\",\"message\":\"\",\"created\":\"2022-05-18\"}]";
        mockMvc.perform(get(URI)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @Order(6)
    @DisplayName("Contact list empty: as Admin you should recive a 204 response")
    void getContacts_AdminShouldSeeMessageWhenContactListIsNull() throws Exception {

        when(contactService.listContacts()).thenThrow(new ResponseStatusException(HttpStatus.NO_CONTENT));

        mockMvc.perform(get(URI)
                        .header("Authorization", "Bearer " + tokenAdmin.getJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Order(7)
    @DisplayName("Get contact: you shouldn't see contact list if you aren't logged")
    void getContacts_ShouldBeForbbidenIfNotLogged() throws Exception {

        mockMvc.perform(get(URI))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @Order(8)
    @DisplayName("Get contact: you shouldn't see contact list if you aren't ADMIN")
    void getContacts_ShouldBeForbbidenIfNotAdminRole() throws Exception {

        mockMvc.perform(get(URI)
                        .header("Authorization", "Bearer " + tokenUser.getJwt()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}


