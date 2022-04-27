package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public Contact contactDto2contactEntity(ContactDto contactDto){
        Contact entity = new Contact();
        entity.setName(contactDto.getName());
        entity.setPhone(contactDto.getPhone());
        entity.setEmail(contactDto.getEmail());
        entity.setMessage(contactDto.getMessage());
        return entity;
    }
}
