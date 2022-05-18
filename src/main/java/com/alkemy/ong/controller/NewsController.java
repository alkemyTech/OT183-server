package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.service.INewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(tags = "News")
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

    @ApiOperation(value = "Get a News by id", notes = "Returns a news as per the id")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully retrieved",
                    response = NewsDto.class),
            @ApiResponse(code = 404, message = "Not Found - The news was not found"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "News id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "1"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable("id") long id) throws EntityNotFoundException {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
    }

    @ApiOperation(
            value = "Update a News",
            notes = "Update an existing News and returns it",
            nickname = "Update News")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The news was updated", response = NewsUpdateDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - News not found as per id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "News id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "1"
            )
    }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateNews(@PathVariable("id") long id,
                                        @Valid @RequestBody @ApiParam(value="news", required = true) NewsUpdateDTO newsUpdate) throws EntityNotFoundException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateNews(id, newsUpdate));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message.getMessage("data.not.found", null, Locale.US));
        }

    }

    @ApiOperation(
            value = "Delete a News",
            notes = "Update an existing News by its id",
            nickname = "Delete News")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The news was deleted"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - News not found as per id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "News id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "1"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.deleteNews(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(
            value = "Create a new News",
            notes = "Create a new News and returns it",
            nickname = "Add News")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The news was created", response = NewsDto.class),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Category not found as per id ")
    })
    @ApiImplicitParams({
     @ApiImplicitParam(
             name = "newsDto",
             value = "News to be added",
             required = true,
             paramType = "body",
             dataType = "NewsDto"
     )
    }
    )
    @PostMapping
    public ResponseEntity<NewsResponseDto> createNews(@Valid @RequestBody @ApiParam(value="news", required = true) NewsDto newsDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNews(newsDto));
    }
}
