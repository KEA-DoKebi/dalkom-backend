package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum InquiryCategory {

	PRODUCT(34L, "상품"),
	ORDER(35L, "주문"),
	MILEAGE(36L, "마일리지"),
	CANCEL(37L, "반품/환불");

	private final Long categorySeq;
	private final String name;

	InquiryCategory(Long categorySeq, String name) {
		this.categorySeq = categorySeq;
		this.name = name;
	}

	public static String getNameByState(Long categorySeq) {
		for (InquiryCategory inquiryCategory : InquiryCategory.values()) {
			if (inquiryCategory.categorySeq.equals(categorySeq)) {
				return inquiryCategory.name;
			}
		}
		return null; // 또는 기본값
	}

}
