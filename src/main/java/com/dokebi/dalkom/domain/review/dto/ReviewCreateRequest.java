package com.dokebi.dalkom.domain.review.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class ReviewCreateRequest {

	@NotNull(message = "ReviewCreateRequest orderDetailSeq NotNull 에러")
	private Long orderDetailSeq;

	@NotNull(message = "ReviewCreateRequest content NotNull 에러")
	@NotBlank(message = "ReviewCreateRequest content NotBlank 에러")
	private String content;

	@NotNull(message = "ReviewCreateRequest rating NotNull 에러")
	@Min(value = 1, message = "ReviewCreateRequest rating Min 에러")
	@Max(value = 5, message = "ReviewCreateRequest rating Max 에러")
	private Integer rating;
}
