package com.orchasp.cis.ContactInformationSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categories_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	@Column(unique=true)
	private String categoryName;
	private String categoryShortName;
//	@ManyToOne
//	@JoinColumn(name="company_id")
//	private Company company;
}
