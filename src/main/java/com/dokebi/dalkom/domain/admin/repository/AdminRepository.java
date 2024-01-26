package com.dokebi.dalkom.domain.admin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.admin.entity.Admin;

import io.lettuce.core.dynamic.annotation.Param;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	boolean existsByNickname(String nickname);

	Optional<Admin> findByAdminId(String adminId);

	Optional<Admin> findByAdminSeq(Long adminSeq);

	@Query("SELECT a FROM Admin a " +
		"WHERE (:adminId IS NULL OR a.adminId LIKE CONCAT('%', :adminId, '%')) " +
		"OR (:name IS NULL OR a.name LIKE CONCAT('%', :name, '%')) " +
		"OR (:nickname IS NULL OR a.nickname LIKE CONCAT('%', :nickname, '%'))")
	Page<Admin> findAdminListBySearch(@Param("adminId") String adminId,
		@Param("name") String name,
		@Param("nickname") String nickname,
		Pageable pageable);
}
