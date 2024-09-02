package com.orchasp.cis.ContactInformationSystem.request;

import javax.validation.constraints.Email;

import com.orchasp.cis.ContactInformationSystem.entity.Company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

	private String fullName;
	private String userName;
	@Email
	private String email;
	private Long mobile;
	private String password;
	private String role;
	private String designation;
	private String status;
	private Company company;
	

	
	
	
}
