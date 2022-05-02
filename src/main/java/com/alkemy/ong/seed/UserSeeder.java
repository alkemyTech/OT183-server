package com.alkemy.ong.seed;

import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.exception.RoleNotFoundException;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@AllArgsConstructor
@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    private final String[] names = {
            "Adriana",
            "Alejandro",
            "Alexander",
            "Andrea",
            "Andres",
            "Angela",
            "Angelica",
            "Angie",
            "Camilo",
            "Carlos",
            "Carol",
            "Carolina",
            "Catherine",
            "Cinthya",
            "Claudia",
            "Cristina",
            "Daniel",
            "Daniela",
            "Diana",
            "Diego"
    };

    private final String[] lastnames = {
            "Hernandez",
            "Sanchez",
            "Acevedo",
            "Vargas",
            "Acero",
            "Garcia",
            "Monroy",
            "Piñeros",
            "Blanco",
            "Fernandez",
            "Ruiz",
            "Rodriguez",
            "Cortes",
            "Gomez",
            "Castellanos",
            "Contreras",
            "Pinzon",
            "Alfonso",
            "Guzman",
            "Torres"
    };

    private enum RoleType {
        ADMIN,
        USER
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.getUsersQuantity() > 0) return;



        if (roleRepository.getRoleQuantity() == 0) {
            createRoles();
        }

        Role userRole = getRole(RoleType.USER.toString());

        Role adminRole = getRole(RoleType.ADMIN.toString());

        for (int i = 0; i < 20; i++) {
            createUser(
                    getFirstname(i),
                    getLastname(i),
                    getEmail(getFirstname(i), getLastname(i)),
                    getPassword(),
                    getPhoto(i),
                    getRoleByIndex(i, userRole, adminRole)
            );
        }

    }

    public void createUser(String firstname, String lastname, String email, String password, String photo, Role role) {
        UserModel user = UserModel.builder()
                .firstName(firstname)
                .lastName(lastname)
                .password(password)
                .email(email)
                .photo(photo)
                .role(role)
                .build();
        userRepository.save(user);
    }

    public void createRoles() {
        roleRepository.save(
                Role.builder()
                        .name(RoleType.USER.toString())
                        .build()
        );
        roleRepository.save(
                Role.builder()
                        .name(RoleType.ADMIN.toString())
                        .build()
        );
    }

    public Role getRole(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(
                                messageSource.getMessage("error.role_not_found", null, Locale.US)
                        )
                );
    }

    public String getPassword() {
        return passwordEncoder.encode("123456789");
    }

    public String getPhoto(int id) {
        return "/assets/photos/user" + (id + 1);
    }

    public String getFirstname(int index) {
        return names[index];
    }

    public String getLastname(int index) {
        return lastnames[index];
    }

    public Role getRoleByIndex(int index, Role userRole, Role adminRole) {
        if (index < 10) return userRole;
        return adminRole;
    }

    public String getEmail(String firstname, String lastname) {
        return firstname.toLowerCase() + "." + lastname.toLowerCase() + "@gmail.com";
    }

}
