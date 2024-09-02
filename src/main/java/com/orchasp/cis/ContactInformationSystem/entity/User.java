package com.orchasp.cis.ContactInformationSystem.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String fullName;
	@NotNull
	private String userName;
	@NotNull
	@Email 
	private String email;
	private Long mobile;

	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	private String designation;
	@Enumerated(EnumType.STRING)
    private Status status;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
}
