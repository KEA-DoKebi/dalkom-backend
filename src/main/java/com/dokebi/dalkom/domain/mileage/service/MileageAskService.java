package com.dokebi.dalkom.domain.mileage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskDto;
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
	private final MileageAskRepository mileageApplyRepository;
	private final UserRepository userRepository;

	public List<MileageAskDto> readMileageAsk() {
		List<MileageApply> mileageApply = mileageApplyRepository.findAll();

		return mileageApply.stream()
			.map(ask -> new MileageAskDto(
				ask.getUser().getUserSeq(),
				ask.getCreatedAt(),
				ask.getUser().getMileage(),
				ask.getAmount(),
				ask.getApprovedState()))
			.collect(Collectors.toList());
	}

	public String putMileageAskState(Long askSeq) {
		String approvedState = mileageApplyRepository.findApproveStateByMilgApplySeq(askSeq);
		log.info(approvedState);

		return approvedState;
	}

	public Response postMileageAsk(Long userSeq, MileageAskRequest request) {
		try {
			User user = userRepository.findByUserSeq(userSeq);

			MileageApply mileageApply = new MileageApply(user, request.getAmount(), "N", null);
			mileageApplyRepository.save(mileageApply);

			return Response.success();
		} catch (UserNotFoundException e) {
			return Response.failure(404, "존재하지 않는 사용자이다.");
		}
	}
}
