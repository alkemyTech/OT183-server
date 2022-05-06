package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.auth.repository.UserRepository;
import com.alkemy.ong.auth.service.CustomUserDetailsService;
import com.alkemy.ong.auth.service.IUserService;
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

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private IUserService iUserService;

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
    public void deleteComment(Long id, HttpServletRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment", "id", id));
        String emailUser = iUserService.getUserProfile(request).getEmail();
        UserModel userModel = userRepository.findByEmail(emailUser);
        Set<Role> roles = userModel.getRoles();
        boolean ban = roles.stream().anyMatch(r -> r.getName().equals("ADMIN"));

        if ((comment.getUser().getId().equals(userModel.getId())) || ban) {
            commentRepository.deleteById(id);
        } else {
            throw new NotAuthorizedException(messageSource.getMessage("error.not_authorized", null, Locale.US));
        }

    }
}
