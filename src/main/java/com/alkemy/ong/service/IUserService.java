package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserBasicDto signup(UserDto userDto) ;
    List<UserBasicDto> returnList();
}
