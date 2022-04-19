package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.model.User;
import com.alkemy.ong.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO user){

       UserDTO userSaved = userService.save(user);

       return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);


    }

    @GetMapping
    public ResponseEntity<List<UserBasicDTO>> getAllUsers(){

        //TODO - When JWT is configured, i am going to modify that only admin users can use this endpoint
        List<UserBasicDTO> userList = userService.returnList();

        return ResponseEntity.status(HttpStatus.CREATED).body(userList);
    }
}
