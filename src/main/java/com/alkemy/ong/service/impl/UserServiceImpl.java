package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.exception.EmailException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.exception.UserRegistrationException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.model.UserModel;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.IUserService;
import com.amazonaws.services.memorydb.model.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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


    @Override
    @Transactional
    public UserBasicDto signup(UserDto userDto) {
        if(emailExists(userDto.getEmail())){
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDto.getEmail());
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserModel entity = userMapper.userDto2UserEntity(userDto);
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

        Optional<UserModel> userModel = userRepository.findByEmail(email);
        UserProfileDto dto = userMapper.userModel2UserProfileDto(userModel);

        return dto;
    }

    public List<UserBasicDto> returnList(){

        List<UserBasicDto> entityList = userRepository.getAllUsers();

        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US)); //new Locale("es","ES")
        }

        return entityList;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
