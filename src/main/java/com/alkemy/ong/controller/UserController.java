package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.auth.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class  UserController {


    private final IUserService userService;



    @GetMapping
    public ResponseEntity<List<UserBasicDto>> getAllUsers(){

        //TODO - When JWT is configured, i am going to modify that only admin users can use this endpoint
        List<UserBasicDto> userList = userService.returnList();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
}
