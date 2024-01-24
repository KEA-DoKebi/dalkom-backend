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
	@Query("SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse("
		+ " m.user.userSeq, m.amount,m.user.mileage, m.approvedState, m.approvedAt) FROM MileageApply m")
	Page<MileageApplyResponse> findAllMileageAsk(Pageable pageable);

	Optional<MileageApply> findByMilgApplySeq(@Param("milgApplySeq") Long milgApplySeq);

	@Query("SELECT COUNT(m) FROM MileageApply m WHERE m.user.userSeq = :userSeq AND m.approvedState IS NULL")
	Long countByUserSeqAndApprovedStateIsNull(@Param("userSeq") Long userSeq);
}
