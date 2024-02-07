package com.dokebi.dalkom.domain.inquiry.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.service.InquiryService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InquiryController {

	private final InquiryService inquiryService;

	// INQUIRY-001 (문의 등록)
	@PostMapping("/api/inquiry/user")
	@ResponseStatus(HttpStatus.OK)
	public Response createInquiry(@LoginUser Long userSeq,
		@Valid @RequestBody InquiryCreateRequest request) {
		inquiryService.createInquiry(userSeq, request);
		return Response.success();
	}

	// INQUIRY-002 (유저별 문의 조회)
	@GetMapping("/api/inquiry/user")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryByUser(@LoginUser Long userSeq, Pageable pageable) {
		return Response.success(inquiryService.readInquiryListByUser(userSeq, pageable));
	}

	// INQUIRY-003 (문의 카테고리 별 문의 조회)
	@GetMapping("/api/inquiry/category/{categorySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryByCategory(@PathVariable Long categorySeq, Pageable pageable) {
		return Response.success(inquiryService.readInquiryListByCategory(categorySeq, pageable));
	}

	// INQUIRY-004 (특정 유저의 본인 문의 수정) 미구현 상태

	// INQUIRY-005 (특정 문의 조회)
	@GetMapping("/api/inquiry/{inquirySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryOne(@PathVariable Long inquirySeq) {
		return Response.success(inquiryService.readInquiryOne(inquirySeq));
	}

	// INQUIRY-006 (문의 답변)
	@PutMapping("/api/inquiry/{inquirySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response answerInquiry(@PathVariable Long inquirySeq,
		@LoginUser Long adminSeq, @Valid @RequestBody InquiryAnswerRequest request) {
		inquiryService.answerInquiry(inquirySeq, adminSeq, request);
		return Response.success();
	}

	// INQUIRY-007 (문의 카테고리 별 문의 검색)
	@GetMapping("/api/inquiry/category/{categorySeq}/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readInquiryByCategorySearch(@PathVariable Long categorySeq,
		@RequestParam(required = false) String title, Pageable pageable) {
		return Response.success(inquiryService.readInquiryListByCategorySearch(categorySeq, title, pageable));
	}

	// INQUIRY-008 (문의 삭제)
	@DeleteMapping("/api/inquiry/{inquirySeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response deleteInquiry(@PathVariable Long inquirySeq) {
		inquiryService.deleteInquiry(inquirySeq);
		return Response.success();
	}
}
