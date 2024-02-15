package com.dokebi.dalkom.domain.inquiry.factory;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryAnswerRequest;

public class InquiryAnswerRequestFactory {
	public static InquiryAnswerRequest createInquiryAnswerRequest() {
		return new InquiryAnswerRequest("answerContent");
	}

	public static InquiryAnswerRequest createInquiryAnswerRequest(String answerContent) {
		return new InquiryAnswerRequest(answerContent);
	}
}
