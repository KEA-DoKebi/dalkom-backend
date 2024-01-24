package com.dokebi.dalkom.domain.mileage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.exception.MileageApplyNotFoundException;
import com.dokebi.dalkom.domain.mileage.repository.MileageApplyRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageApplyService {

	private final MileageApplyRepository mileageApplyRepository;
	private final UserService userService;
	private final MileageService mileageService;

	public Page<MileageApplyResponse> readMileageAsk(Pageable pageable) {
		return mileageApplyRepository.findAllMileageAsk(pageable);
	}

	public MileageApply readByMilgApplySeq(Long milgApplySeq) {
		return mileageApplyRepository.findByMilgApplySeq(milgApplySeq)
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
			mileageApplyRepository.save(mileageApply);
		}
	}

	@Transactional
	public void createMileageAsk(Long userSeq, MileageApplyRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		MileageApply mileageApply = new MileageApply(user, request.getAmount(), "N", null);
		mileageApplyRepository.save(mileageApply);
	}



}
