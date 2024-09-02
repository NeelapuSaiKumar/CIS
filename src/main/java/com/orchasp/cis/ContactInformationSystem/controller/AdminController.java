package com.orchasp.cis.ContactInformationSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.cis.ContactInformationSystem.dto.CompanyDTO;
import com.orchasp.cis.ContactInformationSystem.dto.CustomerDTO;
import com.orchasp.cis.ContactInformationSystem.dto.ServicesDTo;
import com.orchasp.cis.ContactInformationSystem.dto.ServicesIdsDTO;
import com.orchasp.cis.ContactInformationSystem.entity.Categories;
import com.orchasp.cis.ContactInformationSystem.entity.Company;
import com.orchasp.cis.ContactInformationSystem.entity.Customers;
import com.orchasp.cis.ContactInformationSystem.entity.Services;
import com.orchasp.cis.ContactInformationSystem.entity.User;
import com.orchasp.cis.ContactInformationSystem.service.AuthenticationService;
import com.orchasp.cis.ContactInformationSystem.service.CategoriesService;
import com.orchasp.cis.ContactInformationSystem.service.CompanyService;
import com.orchasp.cis.ContactInformationSystem.service.CustomerService;
import com.orchasp.cis.ContactInformationSystem.service.ServicesService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private AuthenticationService authService;

	@Autowired
	private CategoriesService categoryService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ServicesService servicesService;

	@Autowired
	private CompanyService companyService;

	@PostMapping("/company/save")
	public Company createCompany(@RequestBody CompanyDTO company) throws Exception {
		return companyService.save(company);
	}

	@GetMapping("/companies/fetchall")
	public List<Company> getAllCompanies() {
		return companyService.findAll();
	}

	@GetMapping("/company/fetchbyid/{id}")
	public ResponseEntity<Company> getOrganisationById(@PathVariable Long id) {
		Optional<Company> company = companyService.findById(id);
		return company.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/company/update/{id}")
	public ResponseEntity<Company> updateOrganisation(@PathVariable Long id, @RequestBody Company organisationDetails) {
		try {
			Company updatedOrganisation = companyService.updateCompany(id, organisationDetails);
			return ResponseEntity.ok(updatedOrganisation);
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/company/delete/{companyId}")
	public void deleteOrganisation(@PathVariable("companyId") Long companyId) {
		authService.deleteByUserCompanyId(companyId);
		companyService.deleteById(companyId);
		//authService.deletingUser(companyId);
		
	}

	@PostMapping("/approve/{id}")
	public User approveUser(@PathVariable("id") Long id) {
		User user = authService.getByIdUser(id);
		return authService.approveUser(user);
	}

	@PostMapping("/reject/{id}")
	public User rejectUser(@PathVariable("id") Long id) {
		User user = authService.getByIdUser(id);
		return authService.rejectUser(user);
	}

	@PostMapping("/a/{id}")
	public User UserToAdmin(@PathVariable("id") Long id) {
		User user = authService.getByIdUser(id);
		return authService.MakeUserToAdmin(user);
	}
	
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		return authService.updatingUser(id, user);
	}

	@DeleteMapping("/deletebyid/{id}")
	public void delUser(@PathVariable("id") Long id) {
		authService.deletingUser(id);
	}

	@PostMapping("/category/save") 
	public Categories saveCategory(@RequestBody Categories c) {
		return categoryService.createCategory(c);
	}
	
	@PutMapping("/category/update/{id}")
	public Categories updateCategories(@RequestBody Categories c, @PathVariable("id") Long id) {
		return categoryService.editCategory(c,id);
	}

	@DeleteMapping("/category/delete/{categoryId}")
	public void delCategoryById(@PathVariable Long categoryId) {
//		categoryService.removeCategoriesById(id);
		servicesService.deleteServiceByCategoryId(categoryId);
		categoryService.removeCategoriesById(categoryId);
	}

	@DeleteMapping("/category/delete/all")
	public void delAllCards() {
		categoryService.removeAllCategories();
	}

	@PutMapping("/customer/updatebyid/{id}")
	public Customers updateCustomerById(@PathVariable("id") Long id, @RequestBody Customers customer) {
		return customerService.editCustomerById(id, customer);
	}

	@DeleteMapping("/customer/deletebyid/{id}")
	public void deleteCustomerById(@PathVariable("id") Long id) {
		customerService.removeCustomer(id);
	}

	@PostMapping("/service/save")
	public Services createService(@RequestBody ServicesDTo service) {
	    return servicesService.createService(service);
	}
	
	@PutMapping("/service/services/{id}")
	public Services updateService(@PathVariable Long id, @RequestBody Services service) {
		return servicesService.updateService(id, service);
	}

	@DeleteMapping("/service/{id}")
	public void deleteService(@PathVariable Long id) {
		servicesService.deleteService(id);
	}
	
	@PostMapping("/customer/save")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDTO customerdto) {
		  return new ResponseEntity<>(customerService.createCustomer(customerdto), HttpStatus.CREATED);
	}
	
	@PostMapping("/{customerId}/services")
    public ResponseEntity<Customers> addServicesToCustomer(
            @PathVariable Long customerId,
            @RequestBody ServicesIdsDTO serviceIds) {
        Customers customer = customerService.saveCustomerWithServices(customerId, serviceIds.getServiceIds());
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{customerId}/services/{serviceId}")
    public ResponseEntity<Customers> removeServiceFromCustomer(
            @PathVariable Long customerId,
            @PathVariable Long serviceId) {
        Customers customer = customerService.removeServiceFromCustomer(customerId, serviceId);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/customer/create")
	public ResponseEntity<Customers> createCustomer(@RequestBody Customers customer) {
		Customers savedCustomer = customerService.saveCustomer(customer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}
    
    @GetMapping("/getbyid/{id}")
    public User retriveAdminbyId(@PathVariable("id") Long id) {
    	return authService.getByIdAdmin(id);
    }

}
