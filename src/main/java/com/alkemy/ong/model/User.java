package com.alkemy.ong.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

    @Column(name = "firstName", nullable = false, updatable = true)
    private String firstName;
    @Column(name = "lastName", nullable = false, updatable = true)
    private String lastName;
    @Column(name = "email", nullable = false, updatable = true, unique = true)
    private String email;
    @Column(name = "password", nullable = false, updatable = true)
    private String password;
    @Column(name = "photo", nullable = true, updatable = true)
    private String photo;

    //TODO - In the future this roleId will point at to ROLE Class
    private Long roleid;

    private boolean deleted = Boolean.FALSE;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modify_user")
    private Date updated;
}
