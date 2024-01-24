package com.dokebi.dalkom.domain.mileage.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.dto.MileageAskRequest;
import com.dokebi.dalkom.domain.mileage.service.MileageAskService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MileageAskController {

	private final MileageAskService mileageAskService;

	// MILEAGE-003 (마일리지 승인 여부 변경 (관리자))
	@PutMapping("/api/milage/ask/{milgApplySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateMileageAskState(@PathVariable("milgApplySeq") Long milgApplySeq) {

		mileageAskService.updateMileageAskState(milgApplySeq);
		return Response.success();
	}

	// MILEAGE-004 (마일리지 신청 조회 (관리자))
	@GetMapping("/api/mileage/ask")
	@ResponseStatus(HttpStatus.OK)
	public Response readMileageAsk(Pageable pageable) {

		return Response.success(mileageAskService.readMileageAsk(pageable));
	}

	// MILEAGE-005 (마일리지 충전 신청)
	@PostMapping("/api/mileage/ask/user")
	@ResponseStatus(HttpStatus.OK)
	public Response createMileageAsk(@LoginUser Long userSeq,
		@Valid @RequestBody MileageAskRequest request) {

		mileageAskService.createMileageAsk(userSeq, request);
		return Response.success();
	}
}
