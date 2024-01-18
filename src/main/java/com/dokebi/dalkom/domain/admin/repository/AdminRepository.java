package com.dokebi.dalkom.domain.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByNickname(String nickname);

	Admin findByAdminId(String adminId);
	Optional<Admin> findByAdminSeq(Long adminSeq);
}
