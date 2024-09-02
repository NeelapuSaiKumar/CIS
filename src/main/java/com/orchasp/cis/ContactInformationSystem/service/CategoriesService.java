package com.orchasp.cis.ContactInformationSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.entity.Categories;
import com.orchasp.cis.ContactInformationSystem.repository.CategoriesRepository;

@Service
public class CategoriesService {
	@Autowired
private CategoriesRepository categoryRepo;
	
	public Categories createCategory(Categories c) {
		return categoryRepo.save(c);
	}

	public Categories retriveCategoryById(Long id) {
		Optional<Categories> c = categoryRepo.findById(id);
		Categories cc=c.get();
		if(c.isPresent()) {
			return cc;
		}else {
			throw new IllegalArgumentException("Category Not Found With Name "+id);
		}
		
	}


	public List<Categories> retriveAllCategories() {

		return categoryRepo.findAll();
	}

	
	public Categories editCategory(Categories c,Long id) {
		Optional<Categories> o = categoryRepo.findById(id);
		Categories cc = o.get();
		if (o.isPresent()) {
			if (c.getCategoryName() != null) {
				cc.setCategoryName(c.getCategoryName());
			}
			if (c.getCategoryShortName() != null) {
				cc.setCategoryShortName(c.getCategoryShortName());
			}
			return categoryRepo.save(cc);
		} else {
			throw new IllegalArgumentException("ERROR : Something Went Wrong");
		}
	}


	public void removeCategoriesById(Long id) {
		categoryRepo.deleteById(id);
	}

	
	public void removeAllCategories() {
		categoryRepo.deleteAll();
	}
	
	public List<Categories> findByCompanyId(Long companyId){
		return findByCompanyId(companyId);
	}
}
