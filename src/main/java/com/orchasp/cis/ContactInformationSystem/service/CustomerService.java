package com.orchasp.cis.ContactInformationSystem.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.dto.CustomerDTO;
import com.orchasp.cis.ContactInformationSystem.entity.Customers;
import com.orchasp.cis.ContactInformationSystem.entity.Services;
import com.orchasp.cis.ContactInformationSystem.repository.CustomerRepository;
import com.orchasp.cis.ContactInformationSystem.repository.ServicesRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

//	@Autowired
//	private CompanyRepository companyRepo;

	@Autowired
	private ServicesRepository servicesRepo;
	@Transactional
	public Customers createCustomer(CustomerDTO customerdto) {
            Customers customer = new Customers();
	    customer.setPrimaryName(customerdto.getPrimaryName());
	    customer.setSecondaryName(customerdto.getSecondaryName());
	    customer.setPrimaryMobile(customerdto.getPrimaryMobile());
	    customer.setPrimaryEmail(customerdto.getPrimaryEmail());
	    customer.setSecondaryMobile(customerdto.getSecondaryMobile());
	    customer.setSecondaryEmail(customerdto.getSecondaryEmail());
	    customer.setCin(customerdto.getCin());
	    customer.setComTelephone(customerdto.getComTelephone());
	    customer.setMainPerson(customerdto.getMainPerson());
	    customer.setDesignation(customerdto.getDesignation());
	    customer.setPan(customerdto.getPan());
	    customer.setGst(customerdto.getGst());
	    customer.setCompanyName(customerdto.getCompanyName());
	    customer.setCompanyEmail(customerdto.getCompanyEmail());
	    customer.setAddress(customerdto.getAddress());
	    customer.setWebsite(customerdto.getWebsite());
	    customer.setContactNo(customerdto.getContactNo());
	    customer.setState(customerdto.getState());
	    customer.setPincode(customerdto.getPincode());
		return customerRepo.save(customer);
	}

	public Customers getCustomerById(Long id) {
		Optional<Customers> customer1 = customerRepo.findById(id);
		Customers customer2 = customer1.get();
		if (customer1.isPresent()) {
			return customer2;
		} else {
			throw new IllegalArgumentException("Customer Not Found with id " + id);
		}
	}

	public List<Customers> getAllCustomers() {
		List<Customers> customersList = customerRepo.findAll();
		return customersList;
	}

	public Customers editCustomerById(Long customerId, Customers customer) {
		Optional<Customers> customer1 = customerRepo.findById(customerId);
		Customers customer2 = customer1.get();
		if (customer1.isPresent()) {
			if (customer.getPrimaryName() != null) {
				customer2.setPrimaryName(customer.getPrimaryName());
			}
			if (customer.getSecondaryName() != null) {
				customer2.setSecondaryName(customer.getSecondaryName());
			}
			if (customer.getPrimaryEmail() != null) {
				customer2.setPrimaryEmail(customer.getPrimaryEmail());
			}
			if (customer.getPrimaryMobile() != null) {
				customer2.setPrimaryMobile(customer.getPrimaryMobile());
			}
			if (customer.getSecondaryEmail() != null) {
				customer2.setSecondaryEmail(customer.getSecondaryEmail());
			}
			if (customer.getSecondaryMobile() != null) {
				customer2.setSecondaryMobile(customer.getSecondaryMobile());
			}
			if(customer.getCompanyName()!=null) {
				customer2.setCompanyName(customer.getCompanyName());
			}
			if (customer.getComTelephone() != null) {
				customer2.setComTelephone(customer.getComTelephone());
			}
			if (customer.getCompanyEmail() != null) {
				customer2.setCompanyEmail(customer.getCompanyEmail());
			}
			if (customer.getAddress() != null) {
				customer2.setAddress(customer.getAddress());
			}
			if (customer.getWebsite() != null) {
				customer2.setWebsite(customer.getWebsite());
			}
			if (customer.getContactNo() != null) {
				customer2.setContactNo(customer.getContactNo());
			}
			
			if (customer.getState() != null) {
				customer2.setState(customer.getState());
			}
			if (customer.getPincode() != null) {
				customer2.setPincode(customer.getPincode());
			}
			if(customer.getCin()!=null) {
				customer2.setCin(customer.getCin());
			}
			if(customer.getMainPerson()!=null) {
				customer2.setMainPerson(customer.getMainPerson());
			}
			if(customer.getDesignation()!=null) {
				customer2.setDesignation(customer.getDesignation());
			}
			if(customer.getPan()!=null) {
				customer2.setPan(customer.getPan());
			}
			if(customer.getGst()!=null) {
				customer2.setGst(customer.getGst());
			}
//			Company updatedCompany = customer.getCompany();
//			if (updatedCompany != null) {
//				Company existingCompany = customer2.getCompany();
//				if (existingCompany != null) {
//					if (updatedCompany.getCompanyCode() != null) {
//						existingCompany.setCompanyCode(updatedCompany.getCompanyCode());
//					}
//					if (updatedCompany.getCin() != null) {
//						existingCompany.setCin(updatedCompany.getCin());
//					}
//					if (updatedCompany.getCompanyname() != null) {
//						existingCompany.setCompanyname(updatedCompany.getCompanyname());
//					}
//					if (updatedCompany.getDateOfIncorporation() != null) {
//						existingCompany.setDateOfIncorporation(updatedCompany.getDateOfIncorporation());
//					}
//					if (updatedCompany.getRegisterNo() != null) {
//						existingCompany.setRegisterNo(updatedCompany.getRegisterNo());
//					}
//					if (updatedCompany.getTelephone() != null) {
//						existingCompany.setTelephone(updatedCompany.getTelephone());
//					}
//					if (updatedCompany.getCompanyEmail() != null) {
//						existingCompany.setCompanyEmail(updatedCompany.getCompanyEmail());
//					}
//					if (updatedCompany.getAddress() != null) {
//						existingCompany.setAddress(updatedCompany.getAddress());
//					}
//					if (updatedCompany.getWebsite() != null) {
//						existingCompany.setWebsite(updatedCompany.getWebsite());
//					}
//					if (updatedCompany.getContactNo() != null) {
//						existingCompany.setContactNo(updatedCompany.getContactNo());
//					}
//					
//					if (updatedCompany.getState() != null) {
//						existingCompany.setState(updatedCompany.getState());
//					}
//					if (updatedCompany.getPincode() != null) {
//						existingCompany.setPincode(updatedCompany.getPincode());
//					}
//					companyRepo.save(existingCompany);
//				} else {
//					companyRepo.save(updatedCompany);
//					customer.setCompany(updatedCompany);
//				}
//}
			 if (customer.getServices() != null) {
		            customer2.setServices(customer.getServices());
		        }
			return customerRepo.save(customer2);
		} else {
			throw new RuntimeException("Customer Not Found With id " + customerId);
		}
	}


	 public void removeCustomer(Long customerId) {
	        Optional<Customers> optionalCustomer = customerRepo.findById(customerId);
	        if (optionalCustomer.isPresent()) {
	            Customers customer = optionalCustomer.get();
	            customerRepo.delete(customer);
	        } else {
	            throw new UsernameNotFoundException("Customer not found with id " + customerId);
	        }
	    }
	 
	 @Transactional
	    public Customers saveCustomerWithServices(Long customerId, Set<Long> serviceIds) {
	        Customers customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
	        Set<Services> services = new HashSet<>(servicesRepo.findAllById(serviceIds));
	        customer.setServices(services);
	        for (Services service : services) {
	            service.getCustomer().add(customer);
	        }
	        servicesRepo.saveAll(services);
	        return customerRepo.save(customer);
	    }

	    @Transactional
	    public Customers removeServiceFromCustomer(Long customerId, Long serviceId) {
	        Customers customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
	        Services service = servicesRepo.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));

	        customer.getServices().remove(service);
	        service.getCustomer().remove(customer);

	        servicesRepo.save(service);
	        return customerRepo.save(customer);
	    }
	    
	    @Transactional
		public Customers saveCustomer(Customers customer) {
			// Check if the customer with the given primary email already exists
			if (customerRepo.findByPrimaryEmail(customer.getPrimaryEmail()).isPresent()) {
				// Throw a custom exception if the customer already exists
				throw new RuntimeException("Customer with email " + customer.getPrimaryEmail() + " already exists.");
			}

			// Handle null services by initializing an empty set if services are null
			Set<Services> services = customer.getServices() != null ? customer.getServices().stream()
					.map(service -> servicesRepo.findById(service.getServiceId()).orElseThrow(
							() -> new RuntimeException("Service with ID " + service.getServiceId() + " not found")))
					.collect(Collectors.toSet()) : new HashSet<>();

			// Set the validated services to the customer
			customer.setServices(services);

			// Save the customer with the associated services
			return customerRepo.save(customer);
		}

	}

