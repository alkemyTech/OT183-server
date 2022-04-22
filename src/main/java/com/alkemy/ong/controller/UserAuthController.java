package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.service.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class UserAuthController {


    @Autowired
    private IUserAuthService iUserAuthService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile (HttpServletRequest request){

        UserProfileDto dto = iUserAuthService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }
}
