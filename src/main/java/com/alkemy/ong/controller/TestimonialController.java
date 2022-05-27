package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.util.pagination.Pagination;
import com.alkemy.ong.service.impl.TestimonialServiceImpl;
import io.swagger.annotations.*;
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
@Api(tags = "Testimonials")
public class TestimonialController {

    private final TestimonialServiceImpl service;

    @ApiOperation(
            value = "Create a new Testimonial",
            notes = "Create a new Testimonial and returns it",
            nickname = "create Testimonial")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Testimonial was created", response = TestimonialDto.class),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Testimonial not found as per id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "dto",
                    value = "Testimonial to be addedd",
                    required = true,
                    paramType = "body",
                    dataType = "TestimonialDto"
            )}
    )
    @PostMapping
    public ResponseEntity<Object> createTestimonial(@Valid @RequestBody @ApiParam(value="testimonial", required = true) TestimonialDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTestimonial(dto));
    }



    @ApiOperation(
            value = "Delete a Testimonial",
            notes = "Update an existing testimonial by its id",
            nickname = "Delete Testimonial")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The testimonial was deleted"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Testimonial not found as per id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "Testimonial id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "7"
            )}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTestimonial(@PathVariable Long id) {
        service.deleteTestimonial(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "Update a testimonial",
            notes = "Update an existing testimonial and returns it",
            nickname = "Update Testimonial")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The testimonials was updated", response = TestimonialDto.class),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - testimonial not found as per id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "Testimonial id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "5"
            ),
            @ApiImplicitParam(
                    name = "testimonialDto",
                    value = "Testimonial to be added",
                    required = true,
                    paramType = "body",
                    dataType = "TestimonialDto"
            )}
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTestimonial
            (@PathVariable(name = "id") Long id,
             @Valid @RequestBody @ApiParam(value="testimonial", required = true)
                     TestimonialDto testimonialDto){

        return ResponseEntity.ok(service.updateTestimonialById(id,testimonialDto));
    }

    @ApiOperation (value = "Get testimonial", notes = "Get testimonials by size pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved", response = Pagination.class),
            @ApiResponse(code = 404, message = "Not Found - The Testimonials was not found"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "pages",
                    value = "numberPage",
                    required = false,
                    dataType = "Integer"
            )}
    )
    @GetMapping
    public ResponseEntity<Pagination<TestimonialDto>> getAllTestimonialPaged(@PageableDefault(page=0, size=10)Pageable pageable,@RequestParam(required = false) Integer pages){
       return  ResponseEntity.status(HttpStatus.OK).body(service.getAllTestimonialPaged(pageable,pages));
    }
}
