package com.alkemy.ong.controller;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserPatchDto;
import com.alkemy.ong.auth.service.IUserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Api(tags = "Users")
public class  UserController {


    private final IUserService userService;



    @GetMapping
    public ResponseEntity<List<UserBasicDto>> getAllUsers(){

        //TODO - When JWT is configured, i am going to modify that only admin users can use this endpoint
        List<UserBasicDto> userList = userService.returnList();

        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserPatchDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserPatchDto userPatchDto ){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, userPatchDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
