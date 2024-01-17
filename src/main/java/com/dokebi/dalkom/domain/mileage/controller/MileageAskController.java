package com.dokebi.dalkom.domain.mileage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.service.MileageAskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MileageAskController {

	private final MileageAskService mileageAskService;

	// MILEAGE-003 (마일리지 승인 여부 변경 (관리자))
	@PutMapping("/api/milage/ask/{milgApplySeq}")
	public Response putMileageAskState(@PathVariable("milgApplySeq") Long milgApplySeq){
		return  Response.success(mileageAskService.putMileageAskState(milgApplySeq));
	}

	// MILEAGE-004 (마일리지 신청 조회 (관리자))
	@GetMapping("/api/mileage/ask")
	public Response getMileageAsk(){
		return  Response.success(mileageAskService.readMileageAsk());
	}

	// MILEAGE-005 (마일리지 충전 신청)
	@PostMapping("/api/mileage/ask/user/{userSeq}")
	public Response postMileageAsk(){

		return Response.success();
	}

}
