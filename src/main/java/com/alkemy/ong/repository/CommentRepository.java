package com.alkemy.ong.repository;

import com.alkemy.ong.dto.CommentBasicDto;
import com.alkemy.ong.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT new com.alkemy.ong.dto.CommentBasicDto(body) FROM Comment ORDER BY created")
    List<CommentBasicDto> getAllComments();

    @Query(value = "SELECT COUNT(id) FROM Comment")
    int getCommentsQuantity();

    @Query(value = "SELECT * FROM comments WHERE comments.news_id = :newsId", nativeQuery = true)
    List<Comment> findByNewsId(@Param("newsId") Long newsId);
}
