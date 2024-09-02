package com.orchasp.cis.ContactInformationSystem.entity;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="services_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Services {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceId;
	@Column(unique=true)
	private String serviceName;
	private String serviceShortName;
	@ManyToOne
	@JoinColumn(name="category_id")
	private Categories category;
	@ManyToMany(mappedBy = "services")
	@JsonIgnore
	private Set<Customers> customer;
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Services service = (Services) obj;
        return Objects.equals(serviceId,service.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId);
    }
}
