package com.alkemy.ong.model;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "slides")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Slide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String text;

    private String position;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Organization organization;

}