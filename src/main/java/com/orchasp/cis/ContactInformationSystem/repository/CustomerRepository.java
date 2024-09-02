package com.orchasp.cis.ContactInformationSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.cis.ContactInformationSystem.entity.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers, Long> {
//	@Query("SELECT cust FROM Customers cust JOIN FETCH cust.company c WHERE c.companyId = :companyId")
//	List<Customers> findByCompanyId(@Param("companyId") Long companyId);

	@Query("FROM Customers c JOIN FETCH c.services s WHERE s.serviceId = :serviceId")
	List<Customers> findByServiceId(@Param("serviceId") Long serviceId);

	Optional<Customers> findByPrimaryEmail(String primaryEmail);

}
