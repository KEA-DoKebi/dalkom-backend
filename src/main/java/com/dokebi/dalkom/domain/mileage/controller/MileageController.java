package com.dokebi.dalkom.domain.mileage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.mileage.service.MileageAskService;
import com.dokebi.dalkom.domain.mileage.service.MileageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MileageController {
	private final MileageService mileageService;

	// MILEAGE-001 (특정 유저 보유 마일리지 조회)
	@GetMapping("/api/mileage/user/{userSeq}")
	public Response getMileageByUserSeq(@PathVariable("userSeq") Long userSeq) {
		return Response.success(mileageService.readMileageByUserSeq(userSeq));
	}

	// MILEAGE-002 (특정 유저 마일리지 내역 조회)
	@GetMapping("/api/mileage/history/user/{userSeq}")
	public Response getMileageHistoryByUserSeq (@PathVariable("userSeq") Long userSeq){
		return Response.success(mileageService.readMileageHistoryByUserSeq(userSeq));
	}
}
