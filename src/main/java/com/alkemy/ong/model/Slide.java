package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "slides")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE activities SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Setter
@Getter
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    private String text;
    private String order;
    @ManyToOne
    private Organization organizationId;

}
