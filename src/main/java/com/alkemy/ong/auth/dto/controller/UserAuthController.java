package com.alkemy.ong.auth.dto.controller;

import com.alkemy.ong.auth.dto.*;
import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import com.alkemy.ong.auth.service.JwtAuthResponseDto;
import com.alkemy.ong.auth.service.IUserService;
import com.alkemy.ong.auth.service.JwtUtils;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "Show profile info", notes = "Returns profile information" )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully",
                    response = LoginDto.class),
            @ApiResponse(code = 404, message = "Not Found - Invalid profile."),
            @ApiResponse(code = 401, message = "Unauthorized - You can't access to this service"),
            @ApiResponse(code = 403, message = "Forbidden - You don't have permission to access this resource")
    })
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(HttpServletRequest request) {

        UserProfileDto dto = userService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }

    @ApiOperation(value = "Register a new user", notes = "Return a new user registered" )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 201,
                    message = "Successfully created",
                    response = UserDto.class),
            @ApiResponse(code = 404, message = "Not Found - Invalid user or password."),
            @ApiResponse(code = 401, message = "Unauthorized - You can't access to this service"),
            @ApiResponse(code = 403, message = "Forbidden - You don't have permission to access this resource")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "userDto",
                    value = "UserDto",
                    required = true,
                    paramType = "body",
                    dataType = "UserDto"
            )
    }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserBasicDto user = userService.signup(userDto);
            String token = jwtUtils.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new JwtAuthResponseDto(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @ApiOperation(value = "Log an user", notes = "Logs an user and returns user's token" )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully",
                    response = AuthenticationRequest.class),
            @ApiResponse(code = 404, message = "Not Found - Invalid user or password."),
            @ApiResponse(code = 401, message = "Unauthorized - You can't access to this service"),
            @ApiResponse(code = 403, message = "Forbidden - You don't have permission to access this resource")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authRequest",
                    value = "Email and Password",
                    required = true,
                    paramType = "body",
                    dataType = "AuthenticationRequest"
            )
    }
    )
    @PostMapping("/logins")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) throws Exception {

        return ResponseEntity.ok(new AuthenticationResponse(userService.generateToken(authRequest)));
    }
}
