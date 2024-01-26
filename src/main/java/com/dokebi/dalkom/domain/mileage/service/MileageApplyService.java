package com.dokebi.dalkom.domain.mileage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicNumber.MileageApplyState;
import com.dokebi.dalkom.common.magicNumber.MileageHistoryState;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.exception.MileageAlreadyApplyException;
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
	public void updateMileageApply(Long milgApplySeq) {

		MileageApply mileageApply = readByMilgApplySeq(milgApplySeq);
		User user = mileageApply.getUser();

		String approvedState = mileageApply.getApprovedState();
		Integer amount = mileageApply.getAmount();
		Integer totalMileage = user.getMileage() + amount;

		if (approvedState.equals(MileageApplyState.WAIT)) {
			mileageApply.setApprovedState(MileageApplyState.YES);

			mileageService.createMileageHistory(user, amount, totalMileage,
				MileageHistoryState.CHARGED);

			user.setMileage(totalMileage);
		}

	}

	@Transactional
	public void createMileageApply(Long userSeq, MileageApplyRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		// 마일리지 신청 내역 테이블에 대기중인 내역이 있는지 확인.
		isApprovedStateIsWaitByUserSeq(userSeq);
		MileageApply mileageApply = new MileageApply(user, request.getAmount(), MileageApplyState.WAIT);
		mileageApplyRepository.save(mileageApply);

	}

	// 마일리지 신청 내역 테이블에 approvedState가 W(Wait)인 데이터가 존재하면 True.
	private void isApprovedStateIsWaitByUserSeq(Long userSeq) {
		if (mileageApplyRepository.isApprovedStateIsWaitByUserSeq(userSeq))
			throw new MileageAlreadyApplyException();
	}

	public Page<MileageApplyResponse> readMileageAskSearch(String email, String nickname, String name,
		Pageable pageable) {
		return mileageApplyRepository.findAllMileageAskSearch(email, nickname, name, pageable);
	}

}
