package com.alkemy.ong.service;

import com.alkemy.ong.dto.UserProfileDto;

import javax.servlet.http.HttpServletRequest;

public interface IUserAuthService {
    UserProfileDto getUserProfile(HttpServletRequest request);
}
