package com.dokebi.dalkom.domain.mileage.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;

public interface MileageApplyRepository extends JpaRepository<MileageApply, Long> {
	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.approvedState = 'Y' OR m.approvedState = 'N'"
		+ " ORDER BY m.milgApplySeq desc")
	Page<MileageApplyResponse> findAllMileageApply(Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.approvedState = 'W'")
	Page<MileageApplyResponse> findAllMileageApplyWaitState(Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.user.userSeq = :userSeq AND m.approvedState = 'W'")
	Page<MileageApplyResponse> findAllMileageApplyByUserSeq(@Param("userSeq") Long userSeq, Pageable pageable);

	Optional<MileageApply> findByMilgApplySeq(@Param("milgApplySeq") Long milgApplySeq);

	@Query("SELECT COUNT(m) > 0 FROM MileageApply m WHERE m.user.userSeq = :userSeq AND m.approvedState = 'W'")
	boolean isApprovedStateIsWaitByUserSeq(@Param("userSeq") Long userSeq);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ "m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) "
		+ "FROM MileageApply m ")
	Page<MileageApplyResponse> findMileageHistoryApplyList(
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ "m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) "
		+ "FROM MileageApply m WHERE (m.user.email LIKE CONCAT('%', :email, '%')) ")
	Page<MileageApplyResponse> findMileageApplyHistoryListByEmail(@Param("email") String email,
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ "m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) "
		+ "FROM MileageApply m WHERE (m.user.nickname LIKE CONCAT('%', :nickname, '%')) ")
	Page<MileageApplyResponse> findMileageApplyHistoryListByNickname(@Param("nickname") String nickname,
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ "m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) "
		+ "FROM MileageApply m WHERE (m.user.name LIKE CONCAT('%', :name, '%')) ")
	Page<MileageApplyResponse> findMileageApplyHistoryListByName(@Param("name") String name,
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.approvedState = 'W' and (m.user.email LIKE CONCAT('%', :email, '%'))")
	Page<MileageApplyResponse> findAllMileageApplyWaitStateListByEmail(@Param("email") String email, Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.approvedState = 'W' and (m.user.email LIKE CONCAT('%', :nickname, '%'))")
	Page<MileageApplyResponse> findAllMileageApplyWaitStateListByNickname(@Param("nickname") String nickname,
		Pageable pageable);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse( "
		+ " m.milgApplySeq, m.user.userSeq, m.user.name, m.user.email, m.user.nickname, m.amount,m.user.mileage, m.approvedState, m.approvedAt, m.createdAt) FROM MileageApply m"
		+ " WHERE m.approvedState = 'W' and (m.user.email LIKE CONCAT('%', :name, '%'))")
	Page<MileageApplyResponse> findAllMileageApplyWaitStateListByName(@Param("name") String name, Pageable pageable);
}

