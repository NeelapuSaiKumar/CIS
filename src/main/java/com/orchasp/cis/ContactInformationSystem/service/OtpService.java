package com.orchasp.cis.ContactInformationSystem.service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.repository.UserRepository;

@Service
public class OtpService {
	
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserRepository ur;

	@Autowired
	private PasswordEncoder encoder;
	

	private Map<String, String> otpStorage = new HashMap<>();

	public String generateOtp(String identifier) {
		Random random = new Random();
		String otp = String.format("%06d", random.nextInt(999999));
		otpStorage.put(identifier, otp);
		return otp;
	}

	public boolean validateOtp(String identifier, String otp) {
		return otp.equals(otpStorage.get(identifier));
	}

	public void sendOtpEmail(String email, String otp) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("skumarneelapu@gmail.com");
			message.setTo(email);
			message.setSubject("Your OTP Code");
			message.setText("Your OTP code is: " + otp);
			mailSender.send(message);
			System.out.println("Mail sent successfully to " + email);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error sending email: " + e.getMessage());
		}
	}

//	public void sendOtpMobile(Long mobile, String otp) {
//		try {
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setTo(mobile);
//			message.setSubject("Your OTP Code");
//			message.setText("Your OTP code is: " + otp);
//			mailSender.send(message);
//			System.out.println("Mail sent successfully to " + mobile);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Error sending email: " + e.getMessage());
//		}
//		
//	}

	public ResponseEntity<?> sendOtpRegister(String email){
		String otp = generateOtp(email);
		if (email.contains("@")) {
			sendOtpEmail(email, otp);
		} else {
			ResponseEntity.badRequest().body("Invalid Email.....");
		}
		return ResponseEntity.ok("OTP sent");
	}
	
	public ResponseEntity<?> sendOtp(String email) {
		
		Optional<User> userOpt = ur.findByEmail(email);
		//User u=userOpt.get();
		if(!userOpt.isPresent()) {
		throw new IllegalArgumentException("You are not registered with "+email);
		}
//		Optional<User> userOpt = ur.findByEmail(email);
//		if (!userOpt.isPresent()) {
//			return ResponseEntity.badRequest().body("User not found with " + email);
//		}
		User user = userOpt.get();
		String otp = generateOtp(email);
		if (email.contains("@")) {
			sendOtpEmail(user.getEmail(), otp);
		} else {
			ResponseEntity.badRequest().body("Invalid Email.....");
		}
		return ResponseEntity.ok("OTP sent");
	}

	public ResponseEntity<?> verifyOtp(String email, String otp) {
		boolean isValid = validateOtp(email, otp);
		if (isValid) {
			return ResponseEntity.ok("OTP verified");
		} else {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}
	}

	public ResponseEntity<?> changePassword(Long id, String otp, String newPassword) {
		Optional<User> optuser=ur.findById(id);
		User u=optuser.get();
//		sendOtp(u.getEmail());
		boolean isValid = validateOtp(u.getEmail(), otp);
		if (!isValid) {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}

//		Optional<User> userOpt = ur.findByEmail(email);
//		if (!userOpt.isPresent()) {
//			userOpt = ur.findByEmail(email);
//			if (!userOpt.isPresent()) {
//				return ResponseEntity.badRequest().body("User not found");
//			}
//		}
//		User user = optuser.get();
		u.setPassword(encoder.encode(newPassword));
		ur.save(u);
		return ResponseEntity.ok("Password changed successfully");
	}

	public ResponseEntity<?> forgotPassword(String email, String otp, String newPassword) {
		
		Optional<User> userOpt = ur.findByEmail(email);
		User u=userOpt.get();
		if(u!=null) {
		boolean isValid = validateOtp(email, otp);
		if (!isValid) {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}
		}else {
			throw new IllegalArgumentException("You are not registered with "+email);
		}

//		Optional<User> userOpt = ur.findByEmail(email);
//		if (!userOpt.isPresent()) {
//			userOpt = ur.findByEmail(email);
//			if (!userOpt.isPresent()) {
//				return ResponseEntity.badRequest().body("User not found");
//			}
//		}
		
		u.setPassword(encoder.encode(newPassword));
		ur.save(u);
		return ResponseEntity.ok("Password changed successfully");
	}

	public ResponseEntity<?> sendOtpDBmail(Long id) {
		Optional<User> optuser=ur.findById(id);
		User u=optuser.get();
		if(u!=null) {
			return sendOtp(u.getEmail());
		}else {
		throw new NoSuchElementException("Something went wrong......");
		}
	}

	public ResponseEntity<?> verifyOtpDB(Long id, String otp) {
		Optional<User> optuser=ur.findById(id);
		User u=optuser.get();
		boolean isValid = validateOtp(u.getEmail(), otp);
		if (isValid) {
			return ResponseEntity.ok("OTP verified");
		} else {
			return ResponseEntity.badRequest().body("Invalid OTP");
		}
	}
}
