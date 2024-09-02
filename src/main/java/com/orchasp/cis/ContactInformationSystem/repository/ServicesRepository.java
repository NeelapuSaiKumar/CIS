package com.orchasp.cis.ContactInformationSystem.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orchasp.cis.ContactInformationSystem.dto.ServicesIdsDTO;
import com.orchasp.cis.ContactInformationSystem.entity.Services;
@Repository
public interface ServicesRepository extends JpaRepository<Services, Long>{
	@Query("FROM Services s WHERE s.category.id = :categoryId")
    List<Services> findServicesByCategoryId(@Param("categoryId") Long categoryId);
	
	Set<Services> findAllByServiceId(ServicesIdsDTO serviceIds);
	
	@Modifying
    @Query("DELETE FROM Services s WHERE s.category.categoryId = :categoryId")
    void deleteByCategoryId(@Param("categoryId")Long categoryId);
}
