package com.alkemy.ong.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "activities")
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Data
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "Text", nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate updated;

    private boolean deleted;

}
