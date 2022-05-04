package com.alkemy.ong.controller;

import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.auth.service.IUserService;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentUpdateDTO;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    IUserService userService;

    @Autowired
    ICommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> save(@Valid @RequestBody CommentDto comment){

        CommentDto commentSaved = commentService.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") long id, @Valid @RequestBody CommentUpdateDTO comment,HttpServletRequest request){
        //Catched the user logged
        UserProfileDto dto = userService.getUserProfile(request);

        return commentService.updateComment(id, comment, dto);
    }
}
