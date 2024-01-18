package com.dokebi.dalkom.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateRequest {

	@NotNull(message = "ReviewUpdateRequest content NotNull 에러")
	private String content;

	@NotNull(message = "ReviewUpdateRequest rating NotNull 에러")
	@Min(value = 1, message = "ReviewUpdateRequest rating min 에러")
	@Max(value = 5, message = "ReviewUpdateRequest rating max 에러")
	private Integer rating;
}
