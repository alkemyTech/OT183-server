package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserMapper {

    private static LocalDate string2LocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateFormatted = LocalDate.parse(date, formatter);
        return dateFormatted;
    }

    public User userDto2UserEntity(UserDto dto){
        User userEntity = new User();
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setPassword(dto.getPassword());
        userEntity.setPhoto(dto.getPhoto());
        userEntity.setRoleid(dto.getRoleid());
        return userEntity;
    }

    public UserBasicDto userEntity2UserBasicDto(User entity){
        UserBasicDto response = new UserBasicDto(
                entity.getEmail(),
                entity.getFirstName(),
                entity.getLastName()
        );
        return response;
    }

}

    public User userDTO2Entity(UserDTO dto){

        User entity = new User();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        entity.setRoleid(dto.getRoleid());

        return entity;
    }

    public UserDTO userEntity2DTO(User entity){
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setPhoto(entity.getPhoto());
        dto.setCreated(entity.getCreated());
        dto.setUpdated(entity.getUpdated());
        dto.setRoleid(entity.getRoleid());

        return dto;
    }

    public List<UserDTO> listEntity2DTO(List<User> entityList){

        List<UserDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity ->
                dtoList.add(userEntity2DTO(entity))
        );



        return dtoList;
    }

}


