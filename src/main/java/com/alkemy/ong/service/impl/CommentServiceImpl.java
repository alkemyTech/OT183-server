package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    public CommentDto save(CommentDto commentDto) {
        Comment commentModel = commentMapper.commentDto2Model(commentDto);
        commentRepository.save(commentModel);
        CommentDto commentSaveDto = commentMapper.commentModel2Dto(commentModel);
        return commentSaveDto;
    }
}
