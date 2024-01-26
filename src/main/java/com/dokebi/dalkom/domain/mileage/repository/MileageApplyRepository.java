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
		+ " m.user.userSeq, m.amount,m.user.mileage, m.approvedState, m.approvedAt) FROM MileageApply m")
	Page<MileageApplyResponse> findAllMileageAsk(Pageable pageable);

	Optional<MileageApply> findByMilgApplySeq(@Param("milgApplySeq") Long milgApplySeq);

	@Query("SELECT COUNT(m) > 0 FROM MileageApply m WHERE m.user.userSeq = :userSeq AND m.approvedState = 'W'")
	boolean isApprovedStateIsWaitByUserSeq(@Param("userSeq") Long userSeq);

	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ "m.user.userSeq, m.amount,m.user.mileage, m.approvedState, m.approvedAt) "
		+ "FROM MileageApply m "
		+ "WHERE (:email IS NULL OR m.user.email LIKE CONCAT('%', :email, '%')) "
		+ "AND (:nickname IS NULL OR m.user.nickname LIKE CONCAT('%', :nickname, '%')) "
		+ "AND (:name IS NULL OR m.user.name LIKE CONCAT('%', :name, '%'))")
	Page<MileageApplyResponse> findAllMileageAskSearch(@Param("email") String email,
		@Param("nickname") String nickname,
		@Param("name") String name,
		Pageable pageable);
}
