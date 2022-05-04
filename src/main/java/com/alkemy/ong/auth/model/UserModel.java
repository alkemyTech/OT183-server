package com.alkemy.ong.auth.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.Role;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@SQLDelete(sql="UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="{error.empty_field}")
    @Column(nullable = false, updatable = true)
    private String firstName;
    
    @NotNull(message="{error.empty_field}")
    @Column(nullable = false, updatable = true)
    private String lastName;
    
    @NotNull(message="{error.empty_field}")
    @Column(nullable = false, updatable = true, unique = true)
    private String email;
    
    @NotNull(message="{error.empty_field}")
    @Column(nullable = false, updatable = true)
    private String password;
    
    @NotNull
    @Column(nullable = true, updatable = true)
    private String photo;

    @OneToOne
    @JoinColumn(name="role")
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private final boolean deleted = Boolean.FALSE;
    
    @UpdateTimestamp
    @Column(name="modify_user")
    private LocalDate updated;


    @CreationTimestamp
    @Column(name="created_user", updatable = false)
    private LocalDate created;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns=@JoinColumn(name="users_id"),
            inverseJoinColumns=@JoinColumn(name="roles_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }
}
