package com.alkemy.ong.service;

import javax.servlet.http.HttpServletRequest;

import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentUpdateDTO;

import org.springframework.http.ResponseEntity;

public interface ICommentService {
    CommentDto save(CommentDto commentDto);
    ResponseEntity<?> updateComment(Long id, CommentUpdateDTO comment, UserProfileDto dto);
}
