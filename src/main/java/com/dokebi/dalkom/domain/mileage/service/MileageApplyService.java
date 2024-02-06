package com.dokebi.dalkom.domain.mileage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.magicnumber.MileageApplyState;
import com.dokebi.dalkom.common.magicnumber.MileageHistoryState;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyResponse;
import com.dokebi.dalkom.domain.mileage.dto.MileageStateRequest;
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

	@Transactional(readOnly = true)
	public Page<MileageApplyResponse> readMileageApply(Pageable pageable) {
		return mileageApplyRepository.findAllMileageApply(pageable);
	}

	@Transactional(readOnly = true)
	public Page<MileageApplyResponse> readMileageApplyWaitStateList(Pageable pageable) {
		return mileageApplyRepository.findAllMileageApplyWaitState(pageable);
	}

	public Page<MileageApplyResponse> readMileageApplyListByUserSeq(Long userSeq, Pageable pageable) {
		return mileageApplyRepository.findAllMileageApplyByUserSeq(userSeq, pageable);
	}

	public MileageApply readByMilgApplySeq(Long milgApplySeq) {
		return mileageApplyRepository.findByMilgApplySeq(milgApplySeq)
			.orElseThrow(MileageApplyNotFoundException::new);
	}

	@Transactional
	public void updateMileageApply(Long milgApplySeq, MileageStateRequest request) {

		MileageApply mileageApply = readByMilgApplySeq(milgApplySeq);
		User user = mileageApply.getUser();

		String approvedState = mileageApply.getApprovedState();
		Integer amount;
		Integer totalMileage;
		String historyState;

		if (approvedState == null) {
			throw new MileageApplyNotFoundException();
		}

		if (approvedState.equals(MileageApplyState.WAITING.getState())) {

			if (request.getApprovedState().equals(MileageApplyState.YES.getState())) {
				amount = mileageApply.getAmount();
				totalMileage = user.getMileage() + amount;
				historyState = MileageHistoryState.CHARGED.getState();
			} else if (request.getApprovedState().equals(MileageApplyState.NO.getState())) {
				amount = 0;
				totalMileage = user.getMileage();
				historyState = MileageHistoryState.DENIED.getState();
			} else {
				return;
			}

			mileageApply.setApprovedState(request.getApprovedState());

			mileageService.createMileageHistory(user, amount, totalMileage, historyState);

			user.setMileage(totalMileage);
		} else {
			throw new MileageAlreadyApplyException();
		}

	}

	@Transactional
	public void createMileageApply(Long userSeq, MileageApplyRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		// 마일리지 신청 내역 테이블에 대기중인 내역이 있는지 확인.
		isApprovedStateIsWaitByUserSeq(userSeq);
		MileageApply mileageApply = new MileageApply(user, request.getAmount(), MileageApplyState.WAITING.getState());
		mileageApplyRepository.save(mileageApply);

	}

	private void isApprovedStateIsWaitByUserSeq(Long userSeq) {
		// 마일리지 신청 내역 테이블에 approvedState가 W(Wait)인 데이터가 존재하면 True, MileageAlreadyApplyException 반환
		if (mileageApplyRepository.isApprovedStateIsWaitByUserSeq(userSeq))
			throw new MileageAlreadyApplyException();
	}

	public Page<MileageApplyResponse> readMileageApplyHistoryListSearch(String email, String nickname, String name,
		Pageable pageable) {
		if (email != null) {
			return mileageApplyRepository.findMileageApplyHistoryListByEmail(email, pageable);
		} else if (nickname != null) {
			return mileageApplyRepository.findMileageApplyHistoryListByNickname(nickname, pageable);
		} else if (name != null) {
			return mileageApplyRepository.findMileageApplyHistoryListByName(name, pageable);
		} else {
			// 다른 조건이 없는 경우 기본적인 조회 수행
			return mileageApplyRepository.findMileageHistoryApplyList(pageable);
		}
	}

	public Page<MileageApplyResponse> readMileageApplyWaitStateSearch(String email, String nickname, String name,
		Pageable pageable) {
		if (email != null) {
			return mileageApplyRepository.findAllMileageApplyWaitStateListByEmail(email, pageable);
		} else if (nickname != null) {
			return mileageApplyRepository.findAllMileageApplyWaitStateListByNickname(nickname, pageable);
		} else if (name != null) {
			return mileageApplyRepository.findAllMileageApplyWaitStateListByName(name, pageable);
		} else {
			// 다른 조건이 없는 경우 기본적인 조회 수행
			return mileageApplyRepository.findMileageHistoryApplyList(pageable);
		}
	}

}
