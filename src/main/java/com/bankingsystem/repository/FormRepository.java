
package com.bankingsystem.repository;

import com.bankingsystem.entity.FormEntity;
import com.bankingsystem.entity.FormTypeEnum;
import com.bankingsystem.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormRepository extends JpaRepository<FormEntity, Long> {
	List<FormEntity> findByCreatedBy(UserEntity user);

	List<FormEntity> findByType(FormTypeEnum type);
}
