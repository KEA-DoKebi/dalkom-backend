package com.dokebi.dalkom.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReviewUpdateRequest {
	private String content;

	@Min(value = 1, message = "ReviewUpdateRequest rating Min 에러")
	@Max(value = 5, message = "ReviewUpdateRequest rating Max 에러")
	private Integer rating;
}
