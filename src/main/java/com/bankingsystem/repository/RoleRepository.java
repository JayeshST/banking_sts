
	
	package com.bankingsystem.repository;

	import com.bankingsystem.entity.RoleEntity;
import com.bankingsystem.entity.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;
	import java.util.Optional;

	public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	    Optional<RoleEntity> findByRoleName(RoleEnum roleName);
	}

	

