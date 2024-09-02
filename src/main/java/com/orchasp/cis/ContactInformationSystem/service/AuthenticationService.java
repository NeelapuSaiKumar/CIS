package com.orchasp.cis.ContactInformationSystem.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.entity.Company;
import com.orchasp.cis.ContactInformationSystem.entity.Role;
import com.orchasp.cis.ContactInformationSystem.entity.Status;
import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.repository.CompanyRepository;
import com.orchasp.cis.ContactInformationSystem.repository.UserRepository;
import com.orchasp.cis.ContactInformationSystem.request.SignInRequest;
import com.orchasp.cis.ContactInformationSystem.request.SignUpRequest;
import com.orchasp.cis.ContactInformationSystem.response.LoginResponse;
import com.orchasp.cis.ContactInformationSystem.response.MessageResponse;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository ur;

	@Autowired
	private UserDetailsServiceImp userDetailsimp;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JWTServiceImp jwtservice;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CompanyRepository companyRepository;
	
//	@Autowired
//	private OtpService otpservice;

	public ResponseEntity<?> signUp(SignUpRequest request) {
		if (ur.existsByUserName(request.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("UserName Already Exist"));
		}
		if (ur.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email Already Exist"));
		}
		if (ur.existsByMobile(request.getMobile())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Mobile Already Exist"));
		}
		User u = new User();
		u.setFullName(request.getFullName());
		u.setUserName(request.getUserName());
		u.setEmail(request.getEmail());
		u.setMobile(request.getMobile());
		u.setPassword(encoder.encode(request.getPassword()));
		u.setDesignation(request.getDesignation());
		if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
			u.setRole(Role.ADMIN);
			request.setStatus(Status.APPROVED.name());
		} else if (request.getRole() != null && request.getRole().equalsIgnoreCase("USER")) {
			u.setRole(Role.USER);
			request.setStatus(Status.PENDING.name());
		} else {
			throw new IllegalArgumentException("SOMETHING WENT WRONG.........");
		}
		if (request.getRole().equals(Role.ADMIN.name())) {
			u.setStatus(Status.APPROVED);
		} else {
			u.setStatus(Status.PENDING);
		}
		u.setCompany(request.getCompany());

		User uu = ur.save(u);
		emailService.gettingTheMail(uu);

		return ResponseEntity.ok(new MessageResponse("Registered Successfully........."));
	}

	public ResponseEntity<?> signIn(SignInRequest login) {
		try {
			Authentication authenticate = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
			/* .. */
			User user = ur.findByEmail(login.getEmail())
					.orElseThrow(() -> new IllegalArgumentException("User Not Exist with Email " + login.getEmail()));
			UserDetails userDetails = (UserDetailsServiceImp) userDetailsimp.loadUserByUsername(login.getEmail());
			String token = jwtservice.generateToken(userDetails);
			LoginResponse loginRes = new LoginResponse();
			loginRes.setMessage("Login Successfull.........");
			loginRes.setId(user.getId());
			loginRes.setEmail(user.getEmail());
			loginRes.setMobile(user.getMobile());
			loginRes.setUserName(user.getUserName());
			loginRes.setDesignation(user.getDesignation());
			loginRes.setStatus(user.getStatus());
			loginRes.setToken(token);
			loginRes.setRole(user.getRole());
			return ResponseEntity.ok(loginRes);
		} catch (BadCredentialsException exception) {
			exception.printStackTrace();
			return ResponseEntity.status(401).body("ERROR : Invalid Email And Password");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("ERROR : SomeThing Went Wrong");
		}
	}

	public List<User> getUserByRole(Role role) {

		return ur.findByRole(role);
	}

	public User updatingUser(Long id, User user) {
		Optional<User> u = ur.findById(id);
		User uu = u.get();
		if (u.isPresent()) {
			if(user.getFullName()==null) {
				user.setFullName(uu.getFullName());
			}
			if(user.getUserName()==null) {
				user.setUserName(uu.getUserName());
			}
			if (user.getEmail() == null) {
				user.setEmail(uu.getEmail());
			}

			if (user.getDesignation() == null) {
				user.setDesignation(uu.getDesignation());
			}
			if (user.getMobile() == null) {
				user.setMobile(uu.getMobile());
			}
			if (user.getPassword() == null) {
				user.setPassword(encoder.encode(uu.getPassword()));
			} else {
				user.setPassword(uu.getPassword());
			}

//			if (user.getRole() == null) {
//				user.setRole(uu.getRole());
//			}
			Company updatedCompany = uu.getCompany();
			if (updatedCompany != null) {
				Company existingCompany = user.getCompany();
				if (existingCompany != null) {
					if (updatedCompany.getCompanyCode() != null) {
						existingCompany.setCompanyCode(updatedCompany.getCompanyCode());
					}
					if (updatedCompany.getCin() != null) {
						existingCompany.setCin(updatedCompany.getCin());
					}
					if (updatedCompany.getCompanyname() != null) {
						existingCompany.setCompanyname(updatedCompany.getCompanyname());
					}
					if (updatedCompany.getDateOfIncorporation() != null) {
						existingCompany.setDateOfIncorporation(updatedCompany.getDateOfIncorporation());
					}
					if (updatedCompany.getRegisterNo() != null) {
						existingCompany.setRegisterNo(updatedCompany.getRegisterNo());
					}
					if (updatedCompany.getTelephone() != null) {
						existingCompany.setTelephone(updatedCompany.getTelephone());
					}
					if (updatedCompany.getCompanyEmail() != null) {
						existingCompany.setCompanyEmail(updatedCompany.getCompanyEmail());
					}
					if (updatedCompany.getAddress() != null) {
						existingCompany.setAddress(updatedCompany.getAddress());
					}
					if (updatedCompany.getWebsite() != null) {
						existingCompany.setWebsite(updatedCompany.getWebsite());
					}
					if (updatedCompany.getContactNo() != null) {
						existingCompany.setContactNo(updatedCompany.getContactNo());
					}
					if (updatedCompany.getCity() != null) {
						existingCompany.setCity(updatedCompany.getCity());
					}
					if (updatedCompany.getState() != null) {
						existingCompany.setState(updatedCompany.getState());
					}
					if (updatedCompany.getPincode() != null) {
						existingCompany.setPincode(updatedCompany.getPincode());
					}
					companyRepository.save(existingCompany);
				} else {
					companyRepository.save(updatedCompany);
					user.setCompany(updatedCompany);
				}
			}

//			if(uu.getStatus().name()=="PENDING") {
//			if(user.getStatus().name().equalsIgnoreCase(Status.APPROVED.name())){
//				user.setStatus(uu.getStatus());
//				emailService.notifyUserOnApproval(user);
//			}else if (uu.getStatus().name().equalsIgnoreCase(Status.REJECTED.name())){
//				user.setStatus(uu.getStatus());
//				emailService.notifyUserOnRejection(user);
//			}
//			if(uu.getStatus().name()!=null) {
//			if(uu.getStatus().name().equalsIgnoreCase(Status.PENDING.name())) {
//				user.setStatus(Status.PENDING);
//				emailService.notifyUserOnApproval(user);
//			}else if(uu.getStatus()!=Status.PENDING || uu.getStatus().name().equalsIgnoreCase( Status.APPROVED.name())) {
//				user.setStatus(Status.REJECTED);
//				emailService.notifyUserOnRejection(user);
//			}else {
//				user.setStatus(uu.getStatus());
//			}
//			}
			return ur.save(user);
		} else {
			throw new IllegalArgumentException("User Not Found With Id " + id);
		}

	}

	public User getByIdUser(Long id) {
		String role = "USER";
		Optional<User> u = ur.findById(id);
		User uu = u.get();
		if (!u.isPresent()) {
			throw new UsernameNotFoundException("Not Found Record with id " + id + " having " + role);
		} else {
			if (uu.getRole().name().equalsIgnoreCase(role)) {
				return uu;
			} else {
				throw new IllegalArgumentException("User role is not " + role);
			}
		}
	}
	
	public User getByIdAdmin(Long id) {
		String role = "ADMIN";
		Optional<User> u = ur.findById(id);
		User uu = u.get();
		if (!u.isPresent()) {
			throw new UsernameNotFoundException("Not Found Record with id " + id + " having " + role);
		} else {
			if (uu.getRole().name().equalsIgnoreCase(role)) {
				return uu;
			} else {
				throw new IllegalArgumentException("Not Found Record with id " + id + " having " + role +"....");
			}
		}
	}

	public User approveUser(User u) {
		u.setStatus(Status.APPROVED);
		ur.save(u);
		return emailService.notifyUserOnApproval(u);
		}
	

	public User rejectUser(User u) {
		u.setStatus(Status.REJECTED);
		ur.save(u);
		return	emailService.notifyUserOnRejection(u);
		}
	

	public void deletingUser(Long id) {
		ur.deleteById(id);
	}

	public User MakeUserToAdmin(User user) {
		user.setRole(Role.ADMIN);
		ur.save(user);
		return emailService.notifyUserOnMakingAdmin(user);
	}

	public List<User> findByCompanyId(Long company_id) {
		return ur.findByCompanyId(company_id);
	}
	
	@Transactional
	public void deleteByUserCompanyId(Long company_id){
		ur.deleteByCompanyId(company_id);
	}
}
