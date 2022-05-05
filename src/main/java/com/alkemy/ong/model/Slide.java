package com.alkemy.ong.model;

import lombok.*;

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

    private Integer position;

    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organization;

}