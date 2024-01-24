package com.dokebi.dalkom.domain.mileage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;

public interface MileageHistoryRepository extends JpaRepository<MileageHistory, Long> {
	//유저별 마일리지 조회

	@Query("SELECT m.balance FROM MileageHistory m WHERE m.user.userSeq = :userSeq ORDER BY m.createdAt DESC")
	List<Integer> findMileageByUserSeq(@Param("userSeq") Long userSeq);

	//유저 내역 조회
	@Query(value = "SELECT new com.dokebi.dalkom.domain.mileage.dto.MileageHistoryResponse("
		+ "m.type, m.createdAt, m.balance, m.amount) "
		+ "FROM MileageHistory m WHERE m.user.userSeq = :userSeq",
		countQuery = "SELECT COUNT(m) FROM MileageHistory m WHERE m.user.userSeq = :userSeq")
	Page<MileageHistoryResponse> findMileageHistoryListByUserSeq(
		@Param("userSeq") Long userSeq, Pageable pageable);

}
