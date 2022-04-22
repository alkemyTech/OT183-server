package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.model.UserModel;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.IUserAuthService;
import com.alkemy.ong.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserAuthServiceImpl implements IUserAuthService {

    @Autowired
    private JwtUtils jwtTokenUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

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
}
