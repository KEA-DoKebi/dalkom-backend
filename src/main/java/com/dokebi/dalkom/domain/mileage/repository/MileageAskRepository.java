package com.dokebi.dalkom.domain.mileage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;

public interface MileageAskRepository extends JpaRepository<MileageApply, Long> {

	List<MileageApply> findAll();

	Optional<MileageApply> findByMilgApplySeq(@Param("milgApplySeq") Long milgApplySeq);
}
