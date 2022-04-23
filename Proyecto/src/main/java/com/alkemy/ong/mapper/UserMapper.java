package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.model.User;
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

    public User userDTO2Entity(UserDTO dto){

        User entity = new User();
        Optional<Role> role = roleRepository.findById(dto.getId());

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setPhoto(dto.getPhoto());
        entity.setCreated(dto.getCreated());
        entity.setUpdated(dto.getUpdated());
        
        if(role.isPresent()) entity.setRole(role.get());
        
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
        dto.setRole(entity.getRole().getId());

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

