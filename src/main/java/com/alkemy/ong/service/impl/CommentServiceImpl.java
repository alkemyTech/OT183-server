package com.alkemy.ong.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.alkemy.ong.auth.dto.UserProfileDto;
import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.CommentResponseDto;
import com.alkemy.ong.dto.CommentUpdateDTO;
import com.alkemy.ong.dto.response.UpdateCommentsDTO;
import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.NullListException;

import com.alkemy.ong.exception.ParamNotFound;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.ICommentService;
import com.amazonaws.services.managedgrafana.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

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
    
    public ResponseEntity<?> updateComment(Long id, CommentUpdateDTO comment, UserProfileDto dto){
        Optional<Comment> commentRequest = commentRepository.findById(id);
        //Exists the comment?
        if(!commentRequest.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(messageSource.getMessage("comment.not_found", null, Locale.US));
        }
        UserDetails user = customUserDetailsService.loadUserByUsername(dto.getEmail());
        
        if(isAdmin(user)){
            //is a admin
            return ResponseEntity.status(HttpStatus.OK)
            .body(updateComments(commentRequest.get(), comment));
        }else{
            UserModel userRequest = userRepository.getById(commentRequest.get().getUserId());
            if(userRequest.getEmail() == user.getUsername()){
                //user valid to update comment
                return ResponseEntity.status(HttpStatus.OK)
                .body(updateComments(commentRequest.get(), comment));
            }else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(messageSource.getMessage("comment.no_permissions_to_update", null, Locale.US));
            }
        }
    }

    @Override
    public List<CommentResponseDto> getCommentsByNewsId(Long newsId) {
        List<Comment> comments = commentRepository.findByNewsId(newsId);
        if (newsId <= 0){
            throw new ParamNotFound(
                    messageSource.getMessage("error.invalid_param", null, Locale.US));
        }
        if (comments.isEmpty()){
            throw new NullListException(
                    messageSource.getMessage("comment.null_list", null, Locale.US));
        }
        return commentMapper.toResponseDtoList(comments);
    }

    private boolean isAdmin(UserDetails user){
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
        .filter(role -> role.getAuthority() == Role.ADMIN.toString())
        .collect(Collectors.toList());    
        
        return authorities.size() > 0;
    }

    private UpdateCommentsDTO updateComments(Comment commentRequest, CommentUpdateDTO comment){
        commentRequest.setBody(comment.getBody());
        commentRepository.save(commentRequest);
        UpdateCommentsDTO dCommentsDTO = new UpdateCommentsDTO();
        dCommentsDTO.setId(commentRequest.getId());
        dCommentsDTO.setUrl("/comments/"+commentRequest.getId());
        return dCommentsDTO;
    }



}
