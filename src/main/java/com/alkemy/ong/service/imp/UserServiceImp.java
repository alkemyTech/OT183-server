package com.alkemy.ong.service.imp;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.UserMapper;
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

        if (entityList.size() == 0) {
            throw new NullListException("there are no registered users");
        }

        return entityList;
    }
}
