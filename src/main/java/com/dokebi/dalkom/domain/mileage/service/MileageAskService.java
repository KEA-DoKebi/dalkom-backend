package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.mileage.dto.MileageAskRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.exception.MileageAlreadyApplyException;
import com.dokebi.dalkom.domain.mileage.exception.MileageApplyNotFoundException;
import com.dokebi.dalkom.domain.mileage.repository.MileageAskRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageAskService {

	private final MileageAskRepository mileageAskRepository;
	private final UserService userService;
	private final MileageService mileageService;

	public List<MileageAskResponse> readMileageAsk() {

		List<MileageApply> mileageApply = mileageAskRepository.findAll();

		return mileageApply.stream()
			.map(ask -> new MileageAskResponse(
				ask.getUser().getUserSeq(),
				ask.getCreatedAt(),
				ask.getUser().getMileage(),
				ask.getAmount(),
				ask.getApprovedState()))
			.collect(Collectors.toList());
	}

	public MileageApply readByMilgApplySeq(Long milgApplySeq) {
		return mileageAskRepository.findByMilgApplySeq(milgApplySeq)
			.orElseThrow(MileageApplyNotFoundException::new);
	}

	@Transactional
	public void updateMileageAskState(Long milgApplySeq) {

		MileageApply mileageApply = readByMilgApplySeq(milgApplySeq);
		String approvedState = mileageApply.getApprovedState();
		Long userSeq = mileageApply.getUser().getUserSeq();

		if ("N".equals(approvedState)) {
			mileageApply.setApprovedState("Y");
			mileageService.createMileageHistoryAndUpdateUser(userSeq, mileageApply.getAmount(), "1");
			mileageAskRepository.save(mileageApply);
		}
	}

	@Transactional
	public void createMileageAsk(Long userSeq, MileageAskRequest request) {

		User user = userService.readUserByUserSeq(userSeq);

		if (readMileageAskYn(userSeq)) {
			MileageApply mileageApply = new MileageApply(user, request.getAmount(), null);
			mileageAskRepository.save(mileageApply);
		} else {
			throw new MileageAlreadyApplyException();
		}

	}

	// 마일리지 신청 내역 테이블에 approvedState가 Null인 데이터는 사용자 당 1개만 존재해야 함.
	public boolean readMileageAskYn(Long userSeq) {
		Long mileageAskCount = mileageAskRepository.countByUserSeqAndApprovedStateIsNull(userSeq);
		return mileageAskCount < 1;
	}
}
