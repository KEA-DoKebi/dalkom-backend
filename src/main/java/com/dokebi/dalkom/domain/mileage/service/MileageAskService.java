package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskDto;
import com.dokebi.dalkom.domain.mileage.dto.MileageHistoryDto;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.entity.MileageHistory;
import com.dokebi.dalkom.domain.mileage.repository.MileageAskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageAskService {
	private final MileageAskRepository mileageApplyRepository;

	public List<MileageAskDto> readMileageAsk() {
		List<MileageApply> mileageApply = mileageApplyRepository.findAll();

		return mileageApply.stream()
			.map(ask -> new MileageAskDto(
				ask.getUser().getUserSeq(),
				ask.getCreatedAt(),
				ask.getUser().getMileage(),
				ask.getAmount(),
				ask.getApproveState()))
			.collect(Collectors.toList());
	}

	public String putMileageAskState(Long askSeq){
		String approvedState = mileageApplyRepository.findApproveStateByMilgApplySeq(askSeq);
		log.info(approvedState);
		
		return approvedState;
	}
}
