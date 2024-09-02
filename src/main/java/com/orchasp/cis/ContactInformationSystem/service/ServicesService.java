package com.orchasp.cis.ContactInformationSystem.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.dto.ServicesDTo;
import com.orchasp.cis.ContactInformationSystem.entity.Categories;
import com.orchasp.cis.ContactInformationSystem.entity.Services;
import com.orchasp.cis.ContactInformationSystem.repository.CategoriesRepository;
import com.orchasp.cis.ContactInformationSystem.repository.ServicesRepository;


@Service
public class ServicesService {

	@Autowired
	private ServicesRepository servicesRepo;

	@Autowired
	private CategoriesRepository categoriesRepo;

	public Services createService(ServicesDTo service1) {
		Services service=new Services();
		service.setServiceName(service1.getServiceName());
		service.setServiceShortName(service1.getServiceShortName());
		Optional<Categories> categoryOpt = categoriesRepo.findById(service1.getCategory().getCategoryId());
		if (categoryOpt.isPresent()) {
			Categories category = categoryOpt.get();
			service.setCategory(category);
		}
		else {
		throw new RuntimeException("Category not found");
		}
		return servicesRepo.save(service);
	}

	public List<Services> getAllServices() {
		return servicesRepo.findAll();
	}

	
	public Services getServiceById(Long serviceId) {
		return servicesRepo.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
	}

	
	public Services updateService(Long serviceId,Services service1) {
		Services service2= getServiceById(serviceId);
		if(service1.getServiceName()!=null) {
		service2.setServiceName(service1.getServiceName());
		}
		if(service1.getServiceShortName()!=null) {
		service2.setServiceShortName(service1.getServiceShortName());
		}
		Categories c=service2.getCategory();
		if(c!=null) {
			Categories category=service1.getCategory();
			if(category.getCategoryName()!=null) {
				c.setCategoryName(category.getCategoryName());
			}
			if(category.getCategoryShortName()!=null) {
				c.setCategoryShortName(category.getCategoryShortName());
			}
			categoriesRepo.save(c);
		}
		return servicesRepo.save(service2);
	}

	
	public void deleteService(Long serviceId) {
		if (!servicesRepo.existsById(serviceId)) {
			throw new RuntimeException("Service not found");
		}
		servicesRepo.deleteById(serviceId);
	}
	
	public List<Services> findServicesByCategoryId(Long categoryId){
		return servicesRepo.findServicesByCategoryId(categoryId);
	}

	@Transactional
	public void deleteServiceByCategoryId(Long categoryId) {
		
		servicesRepo.deleteByCategoryId(categoryId);
	}
	
}
