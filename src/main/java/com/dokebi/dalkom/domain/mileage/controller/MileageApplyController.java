package com.dokebi.dalkom.domain.mileage.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.dto.MileageApplyRequest;
import com.dokebi.dalkom.domain.mileage.service.MileageApplyService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MileageApplyController {

	private final MileageApplyService mileageApplyService;

	// MILEAGE-003 (마일리지 승인 여부 변경 (관리자))
	@PutMapping("/api/milage/apply/{milgApplySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateMileageApplyState(@PathVariable("milgApplySeq") Long milgApplySeq) {
		mileageApplyService.updateMileageApply(milgApplySeq);
		return Response.success();
	}

	// MILEAGE-004 (마일리지 신청 조회 (관리자))
	@GetMapping("/api/mileage/apply")
	@ResponseStatus(HttpStatus.OK)
	public Response readMileageAsk(Pageable pageable) {
		return Response.success(mileageApplyService.readMileageAsk(pageable));
	}

	// MILEAGE-005 (마일리지 충전 신청)
	@PostMapping("/api/mileage/apply/user")
	@ResponseStatus(HttpStatus.OK)
	public Response createMileageAsk(@LoginUser Long userSeq,
		@Valid @RequestBody MileageApplyRequest request) {
		mileageApplyService.createMileageApply(userSeq, request);
		return Response.success();
	}

	// MILEAGE-006 (마일리지 신청 조회 검색 (관리자))
	@GetMapping("/api/mileage/apply/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readMileageAskSearch(@RequestParam String email,@RequestParam String nickname,@RequestParam String name, Pageable pageable) {

		return Response.success(mileageApplyService.readMileageAskSearch(email,nickname,name,pageable));
	}
}
