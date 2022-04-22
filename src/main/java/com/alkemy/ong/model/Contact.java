package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET delete = true WHERE id = ?")
@Where(clause = "delete = false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{error.empty_field}")
    private String name;

    @NotBlank(message = "{error.empty_field}")
    private String phone;

    @NotBlank(message = "{error.empty_field}")
    @Email(message = "{error.invalid_email}")
    private String email;

    @NotBlank(message = "{error.empty_field}")
    @Size(min = 50, message = "{error.message_size}")
    @Column(columnDefinition = "TEXT")
    private String message;

    private boolean delete = Boolean.FALSE;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

}
