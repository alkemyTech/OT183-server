package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.SlideServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("slides")
public class SlideController {

    private final SlideServiceImpl service;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSlideData(@PathVariable Long id) {
        return new ResponseEntity<>(service.getSlideDetail(id), HttpStatus.OK);
    }

}
