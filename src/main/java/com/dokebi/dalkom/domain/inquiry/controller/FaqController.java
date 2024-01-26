package com.dokebi.dalkom.domain.inquiry.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.inquiry.dto.InquiryCreateRequest;
import com.dokebi.dalkom.domain.inquiry.service.FaqService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {

	private final FaqService faqService;

	// FAQ-001 (FAQ 조회)
	@GetMapping("/api/faq")
	public Response createFaq(@LoginUser Long userSeq,
		@Valid @RequestBody InquiryCreateRequest request) {
		faqService.createFaq(userSeq, request);
		return Response.success();
	}

	// FAQ-002 (FAQ 등록)
	@PostMapping("/api/faq")
	public Response readFaq(@LoginUser Long userSeq,
		@Valid @RequestBody InquiryCreateRequest request) {
		faqService.readFaq(userSeq, request);
		return Response.success();
	}

}
