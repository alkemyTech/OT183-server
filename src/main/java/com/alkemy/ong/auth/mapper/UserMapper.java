package com.alkemy.ong.auth.mapper;


import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.auth.dto.UserDto;
import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.dto.UserPatchDto;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {

    @Autowired
    RoleRepository roleRepository;

    public UserModel userDTO2Entity(UserDto dto){

        UserModel entity = new UserModel();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        
        return entity;
    }

    public UserDto userEntity2DTO(UserModel entity){
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setPhoto(entity.getPhoto());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setRole(entity.getRole());

        return dto;
    }

    public List<UserDto> listEntity2DTO(List<UserModel> entityList){

        List<UserDto> dtoList = new ArrayList<>();

        entityList.forEach(entity ->
                dtoList.add(userEntity2DTO(entity))
        );



        return dtoList;
    }

    public UserBasicDto userEntity2UserBasicDto(UserModel entity) {
        UserBasicDto userBasicDto = new UserBasicDto();
        userBasicDto.setEmail(entity.getEmail());
        userBasicDto.setFirstName(entity.getFirstName());
        userBasicDto.setLastName(entity.getLastName());
        userBasicDto.setId(entity.getId());

        return userBasicDto;

    }

    public UserProfileDto userModel2UserProfileDto(UserModel userModel) {
        UserProfileDto userProfileDto = new UserProfileDto();

        userProfileDto.setEmail(userModel.getEmail());
        userProfileDto.setFirstName(userModel.getFirstName());
        userProfileDto.setLastName(userModel.getLastName());
        userProfileDto.setPhoto(userModel.getPhoto());
        return userProfileDto;

    }

    public UserPatchDto userModel2UserPatchDto(UserModel userModel){
        return new UserPatchDto(userModel.getFirstName(), userModel.getLastName(), userModel.getPhoto());
    }

}


