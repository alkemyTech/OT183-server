package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import java.util.Locale;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private INewsService service;

    @Autowired
    private MessageSource message;

    @GetMapping
    public ResponseEntity<?> getAllPages(@PageableDefault(page=0, size=10) Pageable pageable, @RequestParam (required = false) Integer page ){
        return ResponseEntity.ok().body(service.getAllPages(pageable, page)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") long id) throws EntityNotFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") long id,@Valid @RequestBody NewsUpdateDTO newsUpdate) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateNews(id, newsUpdate));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage("data.not.found", null, Locale.US));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteNews(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody NewsDto newsDto){
        return ResponseEntity.ok(service.createNews(newsDto));
    }
}
