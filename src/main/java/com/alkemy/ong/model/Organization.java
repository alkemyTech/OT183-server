package com.alkemy.ong.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Organization")
@SQLDelete(sql = "UPDATE table_product SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Organization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name must not be empty")
	@Column(name="Name")
	private String name;
	
	@Column(name="Image")
	private String image;
	
	@Column(name="Address")
	private String address;
	
	@Column(name="Phone")
	private int phone;
	
	@NotBlank(message = "Email must not be empty")
	@Column(name="Email")
	private String email;
	
	@Column(name="WelcomeText")
	private String welcomeText;
	
	@Column(name="AboutUsText")
	private String aboutUsText;
	
	@CreationTimestamp
	private Timestamp timeStamp;
	
	private boolean deleted = Boolean.FALSE;
}

/*
 * Use @FilterDef and @Filter above the class definition to make the
 * soft deleted data accesible to, for example, an administrator.
 * */
