package com.alkemy.ong.controller;


import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.auth.service.IUserService;
import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.dto.CommentUpdateDTO;
import com.alkemy.ong.service.ICommentService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("comments")
@Api(tags = "Comments")
public class CommentController {
    @Autowired
    IUserService userService;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") long id, @Valid @RequestBody CommentUpdateDTO comment,HttpServletRequest request){
        //Catched the user logged
        UserProfileDto dto = userService.getUserProfile(request);

        return commentService.updateComment(id, comment, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable(name = "id") Long id, HttpServletRequest request){
        commentService.deleteComment(id,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("posts/{newsId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByNewsId(@PathVariable Long newsId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commentService.getCommentsByNewsId(newsId));
    }
}
