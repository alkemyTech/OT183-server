package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserPatchDto;
import com.alkemy.ong.dto.UserProfileDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IUserService {

    UserBasicDto signup(UserDto userDto) ;

    UserProfileDto getUserProfile(HttpServletRequest request);

    List<UserBasicDto> returnList();

    UserPatchDto updateUser(Long id, UserPatchDto userDto);
}
