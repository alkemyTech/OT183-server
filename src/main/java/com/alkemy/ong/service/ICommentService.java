package com.alkemy.ong.service;


import javax.servlet.http.HttpServletRequest;
import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.dto.CommentUpdateDTO;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICommentService {
    List<CommentBasicDto> getAllComments();
    CommentDto save(CommentDto commentDto);
    ResponseEntity<?> updateComment(Long id, CommentUpdateDTO comment, UserProfileDto dto);
    List<CommentResponseDto> getCommentsByNewsId(Long newsId);
    void deleteComment(Long id, HttpServletRequest request);

}
