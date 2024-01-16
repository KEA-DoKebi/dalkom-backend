package com.dokebi.dalkom.domain.inqury.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.inqury.service.InquiryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InquiryController {

	private final InquiryService inquiryService;

	// INQUIRY-002 (특정 유저의 문의 조회)
	@GetMapping("api/inquiry/user/{userSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryByUser(@PathVariable Long userSeq) {

		return Response.success(inquiryService.getInquiryListByUser(userSeq));
	}

	// INQUIRY-003 (카테고리 별 문의 조회)
	@GetMapping("api/inquiry/category/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryByCategory(@PathVariable Long categorySeq) {

		return Response.success(inquiryService.getInquiryListByCategory(categorySeq));
	}

	// INQUIRY-004 (특정 문의 조회)
	@GetMapping("api/inquiry/{inquirySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryOne(@PathVariable Long inquirySeq) {

		return Response.success(inquiryService.getInquiryOne(inquirySeq));
	}
}
