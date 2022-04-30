package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactBasicDto;
import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("contacts")
public class ContactController {


    @Autowired
    private IContactService contactService;

    @Autowired
    private MessageSource message;

    @PostMapping
    public ResponseEntity<?> addContact(@Valid @RequestBody ContactDto contactDto){
        try{
            contactService.addContact(contactDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(message.getMessage("contact.added", null, Locale.US));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.getMessage("generic.error", null, Locale.US));
        }
    }

    @GetMapping
    public ResponseEntity<List<ContactBasicDto>> getContacts(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.listContacts());
    }
}
