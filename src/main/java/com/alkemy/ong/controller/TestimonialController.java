package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.util.pagination.Pagination;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTestimonial(@PathVariable(name = "id") Long id, @Valid @RequestBody TestimonialDto testimonialDto){
        return ResponseEntity.ok(service.updateTestimonialById(id,testimonialDto));
    }


   @GetMapping
   public ResponseEntity<Pagination<TestimonialDto>> getAllTestimonialPaged(@PageableDefault(page=0, size=10)Pageable pageable,@RequestParam(required = false) Integer page){
       return new ResponseEntity<>(service.getAllTestimonialPaged(pageable,page),HttpStatus.OK);
   }
}
