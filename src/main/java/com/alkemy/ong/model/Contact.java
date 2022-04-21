package com.alkemy.ong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name="contacts")
@SQLDelete(sql = "UPDATE contacts SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{name.null}")
    @NotEmpty(message = "{name.empty}")
    private String name;

    @NotNull(message = "{phone.null}")
    @NotEmpty(message = "{phone.empty}")
    @NotBlank(message = "{field.blank}")
    private String phone;

    @NotBlank(message = "{field.blank}")
    @Email(message = "{email.format}")
    @NotEmpty(message = "{email.empty}")
    private String email;

    @NotBlank(message = "{field.blank}")
    @Size(min = 50, message = "{message.size}")
    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "deleted")
    private boolean deleteAt = Boolean.FALSE;

    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate created;

    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updated;

}
