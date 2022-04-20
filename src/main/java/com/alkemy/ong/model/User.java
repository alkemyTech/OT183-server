package com.alkemy.ong.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql="UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="First name can't be null")
    @Column(nullable = false, updatable = true)
    private String firstName;
    
    @NotNull(message="Last name can't be null")
    @Column(nullable = false, updatable = true)
    private String lastName;
    
    @NotNull(message="Email can't be null")
    @Column(nullable = false, updatable = true, unique = true)
    private String email;
    
    @NotNull(message="Password can't be null")
    @Column(nullable = false, updatable = true)
    private String password;
    
    @NotNull
    @Column(nullable = true, updatable = true)
    private String photo;

    //TODO - In the future this roleId will point at to ROLE Class
    private Long roleid;

    private boolean deleted = Boolean.FALSE;
    
    @UpdateTimestamp

    @Column(name="modify_user")
    private LocalDate updated;

    @CreationTimestamp

    @Column(name="created_user")
    private LocalDate created;
}
