package com.alkemy.ong.model;

import com.alkemy.ong.auth.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "news_id",updatable = false,insertable = false)
    private News news;

    @Column(name = "news_id", nullable = false)
    private Long newsId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false,updatable = false)
    private UserModel user;

    @Column(name = "body", nullable = false,columnDefinition = "TEXT")
    private String body;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

}
