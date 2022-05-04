package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;

import java.util.List;

public interface ICommentService {
    List<CommentBasicDto> getAllComments();
    CommentDto save(CommentDto commentDto);
}
