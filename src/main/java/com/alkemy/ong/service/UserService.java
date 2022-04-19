package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(UserDTO dto);

    List<UserBasicDTO> returnList();
}
