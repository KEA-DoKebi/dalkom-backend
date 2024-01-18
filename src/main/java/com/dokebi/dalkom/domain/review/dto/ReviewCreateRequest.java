package com.dokebi.dalkom.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewCreateRequest {

	@NotNull(message = "ReviewCreateRequest orderDetailSeq notnull 에러")
	private Long orderDetailSeq;

	@NotNull(message = "ReviewCreateRequest content notnull 에러")
	@NotBlank(message = "ReviewCreateRequest content notblank 에러")
	private String content;

	@NotNull(message = "ReviewCreateRequest rating notnull 에러")
	@Min(value = 1, message = "ReviewCreateRequest rating min 에러")
	@Max(value = 5, message = "ReviewCreateRequest rating max 에러")
	private Integer rating;
}
