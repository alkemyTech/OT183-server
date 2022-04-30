package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.model.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {

    public Contact contactDto2contactEntity(ContactDto contactDto) {
        Contact entity = new Contact();
        entity.setName(contactDto.getName());
        entity.setPhone(contactDto.getPhone());
        entity.setEmail(contactDto.getEmail());
        entity.setMessage(contactDto.getMessage());
        return entity;
    }

    public ContactBasicDto contactEntity2ContactBasicDto(Contact contactEntity) {
        ContactBasicDto contactBasicDto = new ContactBasicDto(
                contactEntity.getName(),
                contactEntity.getEmail(),
                contactEntity.getPhone(),
                contactEntity.getMessage(),
                contactEntity.getCreated().toString()
        );
        return contactBasicDto;
    }

    public List<ContactBasicDto> contactEntityList2ContactBasicDtoList(List<Contact> entities) {
        List<ContactBasicDto> dtos = new ArrayList<>();
        entities.forEach(entity -> dtos.add(this.contactEntity2ContactBasicDto(entity)));
        return dtos;
    }
}
