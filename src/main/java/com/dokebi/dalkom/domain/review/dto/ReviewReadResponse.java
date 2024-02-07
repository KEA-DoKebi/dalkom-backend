package com.dokebi.dalkom.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReadResponse {
	private String productName;
	private String imageUrl;
	private String optionName;
	private Integer rating;
	private String content;
}
