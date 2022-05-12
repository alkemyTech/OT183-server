package com.alkemy.ong.auth.dto.controller;

import com.alkemy.ong.auth.dto.LoginDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Log an user")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully",
                    response = LoginDto.class),
            @ApiResponse(code = 404, message = "Not Found - Invalid user or password."),
            @ApiResponse(code = 401, message = "Unauthorized - You can't access to this service"),
            @ApiResponse(code = 403, message = "Forbidden - You don't have permission to access this resource")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "loginDto",
                    value = "Email and Password",
                    required = true,
                    paramType = "body",
                    dataType = "LoginDto"
            )
    }
    )
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
