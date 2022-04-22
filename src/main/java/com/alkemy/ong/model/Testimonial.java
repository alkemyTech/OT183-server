package com.alkemy.ong.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE testimonial SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "testimonials")
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name can't be null")
    @Column(nullable = false)
    private String name;

    private String image;

    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDate created;

    @UpdateTimestamp
    private LocalDate updated;

    @Builder
    public Testimonial(Long id, String name, String image, String content, LocalDate created, LocalDate updated) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }
}
