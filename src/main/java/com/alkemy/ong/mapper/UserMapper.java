package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserProfileDto;
import com.alkemy.ong.model.UserModel;
import org.springframework.stereotype.Component;

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
}
