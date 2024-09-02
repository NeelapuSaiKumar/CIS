package com.orchasp.cis.ContactInformationSystem.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignInRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	private String status;
}
