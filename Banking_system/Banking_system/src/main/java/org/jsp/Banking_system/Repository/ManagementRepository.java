package org.jsp.Banking_system.Repository;

import org.jsp.Banking_system.dto.Management;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagementRepository extends JpaRepository<Management, Integer>{

	Management findByEmail(String email);
	


}
