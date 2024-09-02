package com.orchasp.cis.ContactInformationSystem.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.cis.ContactInformationSystem.entity.Status;
import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.repository.UserRepository;
import com.orchasp.cis.ContactInformationSystem.request.SignInRequest;
import com.orchasp.cis.ContactInformationSystem.request.SignUpRequest;
import com.orchasp.cis.ContactInformationSystem.service.AuthenticationService;
import com.orchasp.cis.ContactInformationSystem.service.OtpService;
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private OtpService otpService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest request){
		return ResponseEntity.ok(authService.signUp(request));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody SignInRequest request){
		Optional<User> u=ur.findByEmail(request.getEmail());
		if(!u.isPresent()) {
			return ResponseEntity.badRequest().body("ACCOUNT NOT FOUND");
		}
		User uu=u.get();
		String status=uu.getStatus().name();
		if(status.equalsIgnoreCase(Status.APPROVED.name())) {
		return ResponseEntity.ok(authService.signIn(request));
		}else {
			throw new IllegalArgumentException("PLEASE WAIT FOR A WHILE,ADMIN NOT APPROVED YOU.........");
		}
	}
	
	@PostMapping("/reqOtp")
	public ResponseEntity<?> sendOtpToMail(@RequestParam String email) {
		return ResponseEntity.ok(otpService.sendOtp(email));
	}

	@PostMapping("/verOtp")
	public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		return ResponseEntity.ok(otpService.verifyOtp(email, otp));
	}

	@PostMapping("/forgot")
	public ResponseEntity<?> forgottenPassword( @RequestParam String email, @RequestParam String otp,@RequestParam String newPassword) {
		return ResponseEntity.ok(otpService.forgotPassword(email, otp, newPassword));
	}
}
