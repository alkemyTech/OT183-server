package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    private MessageSource messageSource;

    public List<CommentBasicDto> getAllComments() {
        if (commentRepository.getCommentsQuantity() == 0) {
            throw new NullListException(
                    messageSource.getMessage("error.null_list", null, Locale.US)
            );
        }
        return commentRepository.getAllComments();
    }

    public CommentDto save(CommentDto commentDto) {
        Comment commentModel = commentMapper.commentDto2Model(commentDto);
        commentRepository.save(commentModel);
        CommentDto commentSaveDto = commentMapper.commentModel2Dto(commentModel);
        return commentSaveDto;
    }
}
