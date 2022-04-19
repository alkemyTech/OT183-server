package com.alkemy.ong.service.imp;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.model.User;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;




    public List<UserBasicDTO> returnList(){

        List<UserBasicDTO> entityList = userRepository.getAllUsers();

        return entityList;
    }
}
