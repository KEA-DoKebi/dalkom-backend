package com.dokebi.dalkom.domain.mileage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.email.dto.EmailMessage;
import com.dokebi.dalkom.common.email.service.EmailService;
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
@Transactional(readOnly = true)
public class MileageApplyService {

	private final MileageApplyRepository mileageApplyRepository;
	private final UserService userService;
	private final MileageService mileageService;
	private final EmailService emailService;

	public Page<MileageApplyResponse> readMileageApply(Pageable pageable) {
		return mileageApplyRepository.findAllMileageApply(pageable);
	}

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
	public void updateMileageApply(Long milgApplySeq, MileageStateRequest request) throws Exception {

		MileageApply mileageApply = readByMilgApplySeq(milgApplySeq);
		User user = mileageApply.getUser();

		String approvedState = mileageApply.getApprovedState();
		Integer amount = mileageApply.getAmount();
		Integer totalMileage = user.getMileage() + amount;

		if (approvedState == null) {
			throw new MileageApplyNotFoundException();
		}

		if (approvedState.equals(MileageApplyState.WAIT.getState())) {
			mileageApply.setApprovedState(request.getApprovedState());

			if (request.getApprovedState().equals(MileageApplyState.YES.getState())) {
				mileageService.createMileageHistory(user, amount, totalMileage,
					MileageHistoryState.CHARGED.getState());
				user.setMileage(totalMileage);

				// 메일 전송
				EmailMessage emailMessage = new EmailMessage(user.getEmail(), "마일리지 신청이 승인되었습니다.");
				emailService.sendMailMileage(emailMessage, "승인", mileageApply.getCreatedAt(), mileageApply.getAmount());
			} else if (request.getApprovedState().equals(MileageApplyState.NO.getState())) {
				mileageService.createMileageHistory(user, 0, user.getMileage(),
					MileageHistoryState.DENIED.getState());

				// 메일 전송
				EmailMessage emailMessage = new EmailMessage(user.getEmail(), "마일리지 신청이 거부되었습니다.");
				emailService.sendMailMileage(emailMessage, "거부", mileageApply.getCreatedAt(), mileageApply.getAmount());
			}
		} else {
			throw new MileageAlreadyApplyException();
		}

	}

	@Transactional
	public void createMileageApply(Long userSeq, MileageApplyRequest request) {
		User user = userService.readUserByUserSeq(userSeq);
		// 마일리지 신청 내역 테이블에 대기중인 내역이 있는지 확인.
		isApprovedStateIsWaitByUserSeq(userSeq);
		MileageApply mileageApply = new MileageApply(user, request.getAmount(), MileageApplyState.WAIT.getState());
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
