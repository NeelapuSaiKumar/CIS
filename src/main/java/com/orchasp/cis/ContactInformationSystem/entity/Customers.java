package com.orchasp.cis.ContactInformationSystem.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="customers_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	private String primaryName;
	private String secondaryName;
	private Long primaryMobile;
	@Email
	private String primaryEmail;
	private Long secondaryMobile;
	@Email
	private String secondaryEmail;
	private String cin;
	@Column(name = "company_land_line")
	private Long comTelephone;
	@NotNull
	private String mainPerson;
	@NotNull
	private String designation;
	private String pan;
	private String gst;
	@Column(name = "company_name")
	private String companyName;
	@Column(name = "company_email")
	@Email
	private String companyEmail;
	@NotNull
	@Column(name = "address")
	private String address;
	
	@Column(name = "company_website",unique = true)
	private String website;

	@Column(name = "contact_no")
	private Long contactNo;
	
	@NotNull
	@Column(name = "state")
	private String state;
	@NotNull
	@Column(name = "pincode")
	private Long pincode;


//	@ManyToOne
//	@JoinColumn(name="company_id")
//	private Company company;
	@ManyToMany(cascade  = CascadeType.ALL)
	@JsonManagedReference
    @JoinTable(name = "customer_services",
               joinColumns= {@JoinColumn(name = "customer_id",referencedColumnName = "customerId")},
               inverseJoinColumns = {@JoinColumn(name = "service_id",referencedColumnName = "serviceId")})
    private Set<Services> services;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customers customer = (Customers) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
