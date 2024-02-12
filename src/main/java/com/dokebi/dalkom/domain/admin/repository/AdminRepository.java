package com.dokebi.dalkom.domain.admin.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;

import io.lettuce.core.dynamic.annotation.Param;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {
	boolean existsByNickname(String nickname);

	Optional<Admin> findByAdminId(String adminId);

	Optional<Admin> findByAdminSeq(Long adminSeq);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) FROM Admin a")
	Page<ReadAdminResponse> findAllAdminList(Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse(" +
		"a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) " +
		"FROM Admin a WHERE a.name LIKE CONCAT('%', :name, '%')")
	Page<ReadAdminResponse> findAdminListByName(
		@Param("name") String name, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.adminId LIKE CONCAT('%', :adminId, '%')) ")
	Page<ReadAdminResponse> findAdminListByAdminId(String adminId, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.depart LIKE CONCAT('%', :depart, '%')) ")
	Page<ReadAdminResponse> findAdminListByDepart(@Param("depart") String depart, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse("
		+ "a.adminSeq, a.adminId, a.role, a.nickname, a.name, a.depart) "
		+ "FROM Admin a WHERE (a.nickname LIKE CONCAT('%', :nickname, '%')) ")
	Page<ReadAdminResponse> findAdminListByNickname(@Param("nickname") String nickname, Pageable pageable);
}
