package com.dokebi.dalkom.domain.mileage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;

public interface MileageApplyRepository extends JpaRepository<MileageApply,Long> {

	List<MileageApply> findAll();
}
