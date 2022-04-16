package com.alkemy.ong.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@SQLDelete(sql = "UPDATE member SET deleted = true WHERE id =?")
@Where(clause = "deleted = false")
@Data
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull(message = "name can't be null")
    @Column(nullable = false)
    private String name;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;

    @NotNull(message = "image can't be null")
    @Column(nullable = false)
    private String image;

    private String description;

    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate updated;

    private boolean deleted;

}

