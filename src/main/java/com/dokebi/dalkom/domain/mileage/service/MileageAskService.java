package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.repository.MileageApplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageAskService {
	private final MileageApplyRepository mileageApplyRepository;

	public List<MileageApply> readMileageAsk() {

		return  mileageApplyRepository.findAll();
	}

}
