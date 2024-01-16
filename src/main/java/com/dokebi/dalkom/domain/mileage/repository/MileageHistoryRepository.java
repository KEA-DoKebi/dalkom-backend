package com.dokebi.dalkom.domain.mileage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryDto;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;

public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long> {
	//유저별 마일리지 조회

	@Query("SELECT m.balance FROM MileageHistory m WHERE m.user.userSeq = :userSeq")
	Integer findMileageByUserSeq(@Param("userSeq") Long userSeq);

	//유저 내역 조회
	@Query("SELECT m FROM MileageHistory m WHERE m.user.userSeq = :userSeq")
	List<MileageHistory> findMileageHistoriesByUserSeq(@Param("userSeq") Long userSeq);
}
