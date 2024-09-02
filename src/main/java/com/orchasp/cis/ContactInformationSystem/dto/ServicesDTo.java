package com.orchasp.cis.ContactInformationSystem.dto;

import com.orchasp.cis.ContactInformationSystem.entity.Categories;

import lombok.Data;

@Data
public class ServicesDTo {
	
	private String serviceName;
	private String serviceShortName;
	private Categories category;
}
