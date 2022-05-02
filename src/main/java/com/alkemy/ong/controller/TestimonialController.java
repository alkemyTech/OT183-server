package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("testimonials")
public class TestimonialController {

    private final TestimonialServiceImpl service;

    @PostMapping
    public ResponseEntity<Object> createTestimonial(@Valid @RequestBody TestimonialDto dto) {
        return new ResponseEntity<>(service.createTestimonial(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTestimonial(@PathVariable Long id) {
        service.deleteTestimonial(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
