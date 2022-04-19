package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserBasicDto;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
