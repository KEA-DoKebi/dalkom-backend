package com.dokebi.dalkom.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewCreateRequest {

	private Long orderDetailSeq;
	private String content;
	private Integer rating;
}
