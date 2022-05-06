package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICommentService {
    List<CommentBasicDto> getAllComments();
    CommentDto save(CommentDto commentDto);
    void deleteComment(Long id, HttpServletRequest request);

}
