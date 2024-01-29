package com.dokebi.dalkom.domain.inquiry.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.inquiry.dto.FaqCreateRequest;
import com.dokebi.dalkom.domain.inquiry.service.FaqService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {

	private final FaqService faqService;

	// FAQ-001 (FAQ 상세 조회)
	@GetMapping("/api/faq/{inquirySeq}")
	public Response readFaqByInquirySeq(@PathVariable Long inquirySeq) {

		return Response.success(faqService.readFaqByInquirySeq(inquirySeq));
	}

	// FAQ-002 (FAQ 전체 조회 )
	@GetMapping("/api/faq")
	public Response readFaqList(Pageable pageable) {

		return Response.success(faqService.readFaqList(pageable));
	}

	// FAQ-003 (FAQ 등록)
	@PostMapping("/api/faq")
	public Response createFaq(@LoginUser Long adminSeq,
		@Valid @RequestBody FaqCreateRequest request) {
		faqService.createFaq(adminSeq, request);
		return Response.success();
	}

}
