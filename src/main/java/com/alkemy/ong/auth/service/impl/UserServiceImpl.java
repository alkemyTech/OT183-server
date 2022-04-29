package com.alkemy.ong.auth.service.impl;

import com.alkemy.ong.auth.dto.AuthenticationRequest;
import com.alkemy.ong.auth.mapper.UserMapper;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserPatchDto;
import com.alkemy.ong.auth.dto.UserDto;
import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.exception.UserAlreadyExistsException;
import com.alkemy.ong.exception.UserNotFoundException;
import com.alkemy.ong.exception.UserRegistrationException;
import com.alkemy.ong.service.impl.MailServiceImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final MessageSource message;
    private final MailServiceImpl mailService;

    @Autowired
    private JwtUtils jwtTokenUtils;

    @Autowired
    AuthenticationManager authenticationManager;



    public String generateToken(AuthenticationRequest authRequest) throws Exception {

        UserDetails userDetails;
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            userDetails = (UserDetails) auth.getPrincipal();
        }
        catch (BadCredentialsException e) {
            throw new Exception(message.getMessage("error.bad_credentials",null,Locale.US),e);
        }
        final String jwt =  jwtTokenUtils.generateToken(userDetails);
        return jwt;
    }

    @Override
    @Transactional
    public UserBasicDto signup(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistsException(message.getMessage("error.account_exists", null, Locale.US));
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserModel entity = userMapper.userDTO2Entity(userDto);
        entity = userRepository.save(entity);
        if (entity != null) {
            mailService.sendEmailByRegistration(entity.getEmail(), entity.getFirstName());
        } else {
            throw new UserRegistrationException(
                    message.getMessage("error.registration", null, Locale.US)
            );
        }

        return userMapper.userEntity2UserBasicDto(entity);
    }

    public UserProfileDto getUserProfile(HttpServletRequest request) {

        String email = null;
        String jwt = null;

        String authorizationHeader = request.getHeader("Authorization");
        jwt = authorizationHeader.substring(7);
        email = jwtTokenUtils.extractUsername(jwt);

        UserModel userModel = userRepository.findByEmail(email);
        UserProfileDto dto = userMapper.userModel2UserProfileDto(userModel);

        return dto;
    }

    public List<UserBasicDto> returnList() {

        List<UserBasicDto> entityList = userRepository.getAllUsers();

        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US)); //new Locale("es","ES")
        }

        return entityList;
    }

    @Override
    @Transactional
    public UserPatchDto updateUser(Long id, UserPatchDto updates) {
        return userRepository.findById(id).map(userModel -> {
            userModel.setFirstName(updates.getFirstName());
            userModel.setLastName(updates.getLastName());
            userModel.setPhoto(updates.getPhoto());
            userModel = userRepository.save(userModel);
            return userMapper.userModel2UserPatchDto(userModel);
        }).orElseThrow(
                () -> new UserNotFoundException(message.getMessage("error.user_not_found", null, Locale.US))
        );

    }

    private boolean emailExists(String email) {

        return userRepository.findByEmail(email) != null;

    }
}
