package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NotAuthorizedException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.Role;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
        Object authentication = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails userDetails = ((UserDetails) authentication);
        String username = userDetails.getUsername();

        UserModel userModel = userRepository.findByEmail(username);
        Set<Role> roles = userModel.getRoles();
        boolean ban = false;
        for (Role role : roles) {
            if (role.getName().equals("ADMIN")) {
                ban = true;
                break;
            }
        }

        if ((comment.getUserId().equals(userModel.getId())) || ban) {
            commentRepository.deleteById(id);
        } else {
            throw new NotAuthorizedException("Not Authorized");
        }


    }
}
