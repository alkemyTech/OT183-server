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

    private enum RoleType {
        ADMIN,
        USER
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.getUsersQuantity() > 0) return;
        String[] names = {
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

        String[] lastnames = {
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

        if (roleRepository.getRoleQuantity() == 0) {
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

        Role userRole = roleRepository.findByName(RoleType.USER.toString())
                .orElseThrow(() -> new RoleNotFoundException(
                                messageSource.getMessage("error.role_not_found", null, Locale.US)
                        )
                );

        Role adminRole = roleRepository.findByName(RoleType.ADMIN.toString())
                .orElseThrow(() -> new RoleNotFoundException(
                                messageSource.getMessage("error.role_not_found", null, Locale.US)
                        )
                );

        for (int i = 0; i < 20; i++) {
            UserModel user = UserModel.builder()
                    .firstName(names[i])
                    .lastName(lastnames[i])
                    .password(passwordEncoder.encode("123456789"))
                    .email(names[i] + "." + lastnames[i] + "@gmail.com")
                    .photo("/assets/photos/user" + (i + 1))
                    .role(i < 10 ? userRole : adminRole)
                    .comments(null)
                    .build();

            userRepository.save(user);
        }

    }


}
