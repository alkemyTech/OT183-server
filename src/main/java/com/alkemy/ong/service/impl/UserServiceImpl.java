package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.model.UserModel;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.IUserService;
import com.amazonaws.services.memorydb.model.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageSource message;


    @Override
    @Transactional
    public UserBasicDto signup(UserDto userDto) {
        if(emailExists(userDto.getEmail())){
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDto.getEmail());
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserModel entity = userMapper.userDto2UserEntity(userDto);
        entity = userRepository.save(entity);
        return userMapper.userEntity2UserBasicDto(entity);
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
