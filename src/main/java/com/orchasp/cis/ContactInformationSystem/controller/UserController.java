package com.orchasp.cis.ContactInformationSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.cis.ContactInformationSystem.entity.Categories;
import com.orchasp.cis.ContactInformationSystem.entity.Customers;
import com.orchasp.cis.ContactInformationSystem.entity.Role;
import com.orchasp.cis.ContactInformationSystem.entity.Services;
import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.service.AuthenticationService;
import com.orchasp.cis.ContactInformationSystem.service.CategoriesService;
import com.orchasp.cis.ContactInformationSystem.service.CustomerService;
import com.orchasp.cis.ContactInformationSystem.service.OtpService;
import com.orchasp.cis.ContactInformationSystem.service.ServicesService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private CategoriesService categoryService;
	
	@Autowired
	private CustomerService customerService;
	
	 @Autowired
	    private ServicesService servicesService;
	
	 @Autowired
		private OtpService otpService;
	 
//	 @Autowired
//		private ServicesRepository serviceRepo;
	 
//	@Autowired
//	private CompanyService companyService;
	
@GetMapping("/getbyid/{id}")
public User retriveUserbyId(@PathVariable("id") Long id) {
	return authService.getByIdUser(id);
}
@GetMapping("/getall/all")
public List<User> retrieveByRoleAdmin(@RequestParam String role) {
   if ("ADMIN".equalsIgnoreCase(role)) {
       return authService.getUserByRole(Role.ADMIN);
   } else if ("USER".equalsIgnoreCase(role)) {
       return authService.getUserByRole(Role.USER);
   } else {
       throw new IllegalArgumentException("Invalid role: " + role);
   }
}

//@PostMapping("/category/save") 
//public Categories saveCategory(@RequestBody Categories c) {
//	return categoryService.createCategory(c);
//}

@GetMapping("/categories/fetchall") 
public List<Categories> gettingAllCategories() {
	return categoryService.retriveAllCategories();
}

@GetMapping("/category/fetchbyid/{id}")
public Categories getCategoriesById(@PathVariable Long id) {
	return categoryService.retriveCategoryById(id);
}

//@PostMapping("/customer/save")
//public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerdto) {
//	  return new ResponseEntity<>(customerService.createCustomer(customerdto), HttpStatus.CREATED);
//}
@GetMapping("/customer/fetchbyid/{id}")
public Customers retriveCustomerById(@PathVariable("id") Long id) {
	return customerService.getCustomerById(id);
}

@GetMapping("/customer/fetchall")
public List<Customers> retriveAllCustomers() {
	return customerService.getAllCustomers();
}


//@PostMapping("/service/save")
//public Services createService(@RequestBody Services service) {
//    return servicesService.createService(service);
//}

@GetMapping("/service/fetchall")
public List<Services> getAllServices() {
    return servicesService.getAllServices();
}

@GetMapping("/service/{serviceId}")
public Services getServiceById(@PathVariable Long serviceId) {
    return servicesService.getServiceById(serviceId);
}

@GetMapping("/service/category/{categoryId}")
public List<Services> getServicesByCategoryId(@PathVariable Long categoryId) {
    return servicesService.findServicesByCategoryId(categoryId);
}

@PostMapping("/verifyEmail")
public ResponseEntity<?> emailValidation(@RequestParam String email) {
	return ResponseEntity.ok(otpService.sendOtpRegister(email));
}

@PostMapping("/requestOtp/{id}")
public ResponseEntity<?> sendOtpToMail(@PathVariable Long id) {
	return ResponseEntity.ok(otpService.sendOtpDBmail(id));
}

@PostMapping("/verifyOtp/{id}")
public ResponseEntity<?> verifyOtp(@PathVariable Long id, @RequestParam String otp) {
	return ResponseEntity.ok(otpService.verifyOtpDB(id, otp));
}

@PostMapping("/changePassword/{id}")
public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestParam String otp,@RequestParam String newPassword) {
	return ResponseEntity.ok(otpService.changePassword(id, otp, newPassword));
}

@GetMapping("/company/{company_id}")
public ResponseEntity<List<User>> getUsersByCompanyId(@PathVariable Long company_id) {
    List<User> user = authService.findByCompanyId(company_id);
    return ResponseEntity.ok(user);
}


}
