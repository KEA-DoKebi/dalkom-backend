package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskResponse;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskRequest;
import com.dokebi.dalkom.domain.mileage.entity.MileageApply;
import com.dokebi.dalkom.domain.mileage.repository.MileageAskRepository;
import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;
import com.dokebi.dalkom.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MileageAskService {
	private final MileageAskRepository mileageAskRepository;
	private final UserRepository userRepository;
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
	@Transactional
	public String putMileageAskState(Long milgApplySeq) {
		MileageApply mileageApply = mileageAskRepository.findByMilgApplySeq(milgApplySeq);
		String approvedState = mileageApply.getApprovedState();
		Long userSeq = mileageApply.getUser().getUserSeq();
		if ("N".equals(approvedState)) {
			mileageApply.setApprovedState("Y");
			mileageService.createMileageHistoryAndUpdateUser(userSeq,mileageApply.getAmount(), "1");
			mileageAskRepository.save(mileageApply);

		}
		return "ok";

	}
	public Response postMileageAsk(Long userSeq, MileageAskRequest request) {
		try {
			User user = userRepository.findByUserSeq(userSeq);

			MileageApply mileageApply = new MileageApply(user, request.getAmount(), "N", null);
			mileageAskRepository.save(mileageApply);

			return Response.success();
		} catch (UserNotFoundException e) {
			return Response.failure(404, "존재하지 않는 사용자이다.");
		}
	}
}
