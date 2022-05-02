package com.alkemy.ong.model;

import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{error.empty_field}")
	private String name;
	
	private String image;
	
	private String address;
	
	private String phone;

	@NotBlank(message = "{error.empty_field}")
	private String email;
	
	private String welcomeText;
	
	private String aboutUsText;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDate created;

	@UpdateTimestamp
	private LocalDate updated;

	@Column(columnDefinition = "boolean default false")
	private final boolean deleted = Boolean.FALSE;

}

/*
 * Use @FilterDef and @Filter above the class definition to make the
 * soft deleted data accesible to, for example, an administrator.
 * */
