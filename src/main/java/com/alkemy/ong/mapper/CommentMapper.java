package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment commentDto2Model(CommentDto dto) {

        Comment model = new Comment();
        model.setNewsId(dto.getNewsId());
        model.setUserId(dto.getUserId());
        model.setBody(dto.getBody());
        return model;
    }

    public CommentDto commentModel2Dto(Comment model) {
        CommentDto dto = new CommentDto();
        dto.setId(model.getId());
        dto.setNewsId(model.getNewsId());
        dto.setUserId(model.getUserId());
        dto.setBody(model.getBody());
        return dto ;
    }
}
