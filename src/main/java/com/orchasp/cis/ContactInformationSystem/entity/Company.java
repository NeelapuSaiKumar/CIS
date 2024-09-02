package com.orchasp.cis.ContactInformationSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long companyId;

	@Column(name = "company_code")
	private String companyCode;

	@Column(name = "cin")
	private String cin;

	@Column(name = "company_name")
	private String companyname;

	
	@Column(name = "date_of_incorporation")
	private String dateOfIncorporation;
	
	@Column(name = "register_no")
	private Long registerNo;

	@Column(name = "company_land_line")
	private Long telephone;
	
	@Column(name = "company_email",unique = true)
	@Email
	private String companyEmail;
	@NotNull
	@Column(name = "address")
	private String address;
	
	@Column(name = "company_website",unique = true)
	private String website;

	@Column(name = "contact_no")
	private Long contactNo;
	
	@Column(name = "city")
	private String city;
	
	@NotNull
	@Column(name = "state")
	private String state;
	@NotNull
	@Column(name = "pincode")
	private Long pincode;
}
