package com.alkemy.ong.dto;

import com.alkemy.ong.auth.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private String body;
    private String user;
    private String created;

}
