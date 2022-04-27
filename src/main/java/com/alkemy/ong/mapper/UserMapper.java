package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class UserMapper {

    public UserProfileDto userModel2UserProfileDto(UserModel model){

        UserProfileDto dto = new UserProfileDto();
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setEmail(model.getEmail());
        dto.setPhoto(model.getPhoto());

        return dto;
    }

    private static LocalDate string2LocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        return dateFormatted;
    }

    public UserModel userDto2UserEntity(UserDto dto){
        UserModel userEntity = new UserModel();
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPassword(dto.getPassword());
        userEntity.setPhoto(dto.getPhoto());
        userEntity.setRoleid(dto.getRoleid());
        return userEntity;
    }

    public UserBasicDto userEntity2UserBasicDto(UserModel entity){
        UserBasicDto response = new UserBasicDto();
        response.setId(entity.getId());
        response.setEmail(entity.getEmail());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        return response;
    }


    public UserModel userDTO2Entity(UserDto dto){

        UserModel entity = new UserModel();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        entity.setRoleid(dto.getRoleid());

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
        dto.setCreated(entity.getCreated().toString());
        dto.setUpdated(entity.getUpdated().toString());
        dto.setRoleid(entity.getRoleid());

        return dto;
    }

    public List<UserDto> listEntity2DTO(List<UserModel> entityList){

        List<UserDto> dtoList = new ArrayList<>();

        entityList.forEach(entity ->
                dtoList.add(userEntity2DTO(entity))
        );
        return dtoList;
    }

}


