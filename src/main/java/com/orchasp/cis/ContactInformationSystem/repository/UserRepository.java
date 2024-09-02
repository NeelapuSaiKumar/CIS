package com.orchasp.cis.ContactInformationSystem.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.orchasp.cis.ContactInformationSystem.entity.Role;
import com.orchasp.cis.ContactInformationSystem.entity.Status;
import com.orchasp.cis.ContactInformationSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUserName(String userName);

	Optional<User> findByEmail(String email);

	Boolean existsByUserName(String userName);

	Boolean existsByMobile(Long mobile);

	Boolean existsByEmail(String email);
  
	List<User> findByRole(Role role);
	
	Optional<User> findByRoleAndStatus(String role, Status status);
	
	@Query("FROM User user JOIN FETCH user.company c WHERE c.companyId = :companyId")
	List<User> findByCompanyId(@Param("companyId") Long companyId);


	 @Modifying
	    @Query("DELETE FROM User u WHERE u.company.companyId = :companyId")
	    void deleteByCompanyId(@Param("companyId")Long companyId);
}
