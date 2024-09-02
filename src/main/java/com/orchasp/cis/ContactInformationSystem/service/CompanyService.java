package com.orchasp.cis.ContactInformationSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.cis.ContactInformationSystem.dto.CompanyDTO;
import com.orchasp.cis.ContactInformationSystem.entity.Company;
import com.orchasp.cis.ContactInformationSystem.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    public Company save(CompanyDTO company){
        Company c = new Company();
        c.setAddress(company.getAddress());
        c.setCin(company.getCin());
        c.setCompanyCode(company.getCompanyCode());
        c.setCompanyname(company.getCompanyname());
        c.setContactNo(company.getContactNo());
        c.setDateOfIncorporation(company.getDateOfIncorporation());
        c.setCompanyEmail(company.getCompanyEmail());
        c.setPincode(company.getPincode());
        c.setRegisterNo(company.getRegisterNo());
        c.setState(company.getState());
        c.setTelephone(company.getTelephone());
        c.setWebsite(company.getWebsite());
        c.setCity(company.getCity());
        return companyRepository.save(c);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Optional<Company> existingCompanyOpt = companyRepository.findById(id);
        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();
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
          
            if (updatedCompany.getState() != null) {
                existingCompany.setState(updatedCompany.getState());
            }
            if (updatedCompany.getPincode() != null) {
                existingCompany.setPincode(updatedCompany.getPincode());
            }

            return companyRepository.save(existingCompany);
        } else {
            throw new RuntimeException("Company not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        if (companyOpt.isPresent()) {
           companyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Company not found with id " + id);
        }
    }

   
}
