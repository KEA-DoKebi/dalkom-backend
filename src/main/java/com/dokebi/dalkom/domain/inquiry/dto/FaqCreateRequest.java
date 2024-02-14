package com.dokebi.dalkom.domain.inquiry.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FaqCreateRequest {
	@NotNull(message = "FaqCreateRequest title NotNull 에러")
	@NotBlank(message = "FaqCreateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "FaqCreateRequest content NotNull 에러")
	@NotBlank(message = "FaqCreateRequest content NotBlank 에러")
	private String content;
}
