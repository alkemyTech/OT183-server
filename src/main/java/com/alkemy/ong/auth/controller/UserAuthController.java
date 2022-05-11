package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.AuthenticationRequest;
import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.auth.dto.UserDto;
import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import com.alkemy.ong.auth.service.JwtAuthResponseDto;
import com.alkemy.ong.auth.service.IUserService;
import com.alkemy.ong.auth.service.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(HttpServletRequest request) {

        UserProfileDto dto = userService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserBasicDto user = userService.signup(userDto);
            String token = jwtUtils.createToken(userDetailsService.loadUserByUsername(user.getEmail()));
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new JwtAuthResponseDto(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authRequest) throws Exception {

        return ResponseEntity.ok(userService.generateToken(authRequest));
    }
}
