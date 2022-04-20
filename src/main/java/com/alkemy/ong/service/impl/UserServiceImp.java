package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource message;




    public List<UserBasicDTO> returnList(){

        List<UserBasicDTO> entityList = userRepository.getAllUsers();

        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null,Locale.US)); //new Locale("es","ES")
        }

        return entityList;
    }
}
