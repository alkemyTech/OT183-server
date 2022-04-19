package com.alkemy.ong.model;

import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "news")
@Getter
@Setter
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")

public class NewsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty(message = "Name may not be empty")
    @Size(min = 2, message = "Name Name cannot be less than 2 characters")
    private String name;
    @NotEmpty(message = "content may not be empty")
    @Column(columnDefinition = "TEXT")
    private String content;
    @NotEmpty(message = "image may not be empty")
    private String image;
    private Boolean deleted = Boolean.FALSE;

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",updatable = false,insertable = false)
    private CategoryModel category;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    private LocalDate updatedDate;


    /*Criterios de aceptación:
    Nombre de tabla: news. Los campos son:
    name: VARCHAR NOT NULL
    content: TEXT NOT NULL
    image: VARCHAR NOT NULL
    categoryId: Clave foranea hacia ID de Categories
    timestamps y softDelete*/
}
