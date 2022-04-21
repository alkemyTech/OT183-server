package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;

public interface IUserService {

    UserBasicDto signup(UserDto userDto) ;
}
