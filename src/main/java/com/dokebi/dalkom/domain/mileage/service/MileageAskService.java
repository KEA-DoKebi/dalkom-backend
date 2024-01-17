package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.repository.MileageAskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageAskService {
	private final MileageAskRepository mileageAskRepository;
	private final MileageService mileageService;

	public List<MileageAskResponse> readMileageAsk() {
		List<MileageApply> mileageApply = mileageAskRepository.findAll();

		return mileageApply.stream()
			.map(ask -> new MileageAskResponse(
				ask.getUser().getUserSeq(),
				ask.getCreatedAt(),
				ask.getUser().getMileage(),
				ask.getAmount(),
				ask.getApproveState()))
			.collect(Collectors.toList());
	}
	@Transactional
	public String putMileageAskState(Long milgApplySeq) {
		//milgApplySeq 로 approvedState 랑 userSeq 불러온다.
		//approvedState N 인경우 Y로 바꾼다.
		//userSeq를 이용해서 createMileageHistoryByUserSeq()를 통해서 내역 추가한다.
		//내역 추가후에는 유저의 마일리도 업데이트 되야한다.
		MileageApply mileageApply = mileageAskRepository.findByMilgApplySeq(milgApplySeq);
		String approvedState = mileageApply.getApproveState();
		Long userSeq = mileageApply.getUser().getUserSeq();
		if ("N".equals(approvedState)) {
			mileageApply.setApproveState("Y");
			mileageService.createMileageHistoryAndUpdateUser(userSeq,mileageApply.getAmount());
			mileageAskRepository.save(mileageApply);

		}
		return "ok";
	}



}
