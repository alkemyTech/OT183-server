package com.alkemy.ong.auth.service;

import com.alkemy.ong.model.Role;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource message;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(message.getMessage("error.not_found_email", null, Locale.US));
        return new User(user.getEmail(), user.getPassword(), mappingRoles(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mappingRoles(Role role) {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

}
