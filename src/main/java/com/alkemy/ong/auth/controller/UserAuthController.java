package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.auth.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController

public class UserAuthController {

    @Autowired
    private IUserAuthService iUserAuthService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile (HttpServletRequest request){

        UserProfileDto dto = iUserAuthService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }
}
