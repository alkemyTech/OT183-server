package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentBasicDto>> getAllComments() {
        return new ResponseEntity<>(
                commentService.getAllComments(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CommentDto> save(@Valid @RequestBody CommentDto comment){

        CommentDto commentSaved = commentService.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable(name = "id") Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
