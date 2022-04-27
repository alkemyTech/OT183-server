package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.service.IUserService;
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

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile (HttpServletRequest request){

        UserProfileDto dto = userService.getUserProfile(request);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    userService.signup(userDto)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
