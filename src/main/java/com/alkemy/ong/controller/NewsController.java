package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.NewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsServiceImpl service;

    @Autowired
    private MessageSource message;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getNewsById(@PathVariable("id") long id) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage("data.not.found", null, Locale.US));
        }

    }
}
