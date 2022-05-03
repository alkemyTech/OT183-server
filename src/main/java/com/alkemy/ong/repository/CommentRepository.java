package com.alkemy.ong.repository;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT new com.alkemy.ong.dto.CommentBasicDto(body) FROM Comment ORDER BY created")
    List<CommentBasicDto> getAllComments();

}
