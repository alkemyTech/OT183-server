package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.model.Comment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public CommentResponseDto toResponseDto(Comment comment){
        return new CommentResponseDto(
                comment.getBody(),
                comment.getUser().getFirstName()
                        .concat(" ")
                        .concat(comment.getUser().getLastName()),
                comment.getCreated().toString()
        );
    }

    public List<CommentResponseDto> toResponseDtoList(List<Comment> comments) {
        List<CommentResponseDto> response = new ArrayList<>();
        comments.forEach(comment -> {
            response.add(toResponseDto(comment));
        });
        return response;
    }
}
