package com.orchasp.cis.ContactInformationSystem.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orchasp.cis.ContactInformationSystem.entity.Role;
import com.orchasp.cis.ContactInformationSystem.entity.Status;

import lombok.Data;

@Data
public class LoginResponse {
	private Long id;
	private String token;
	private String type="Bearer";
	private Role role;
	private Status status;
	private String userName;
	@JsonIgnore
	private String password;
	private String message;
	private String companyEmail;
	private String designation;
	private String email;
	private Long mobile;
	
}
