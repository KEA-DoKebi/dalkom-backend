package com.dokebi.dalkom.domain.notice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUpdateRequest {

	@NotNull(message = "NoticeUpdateRequest title notnull 에러")
	@NotBlank(message = "NoticeUpdateRequest title notblank 에러")
	private String title;

	@NotNull(message = "NoticeUpdateRequest content notnull 에러")
	@NotBlank(message = "NoticeUpdateRequest content notblank 에러")
	private String content;

	@NotNull(message = "NoticeUpdateRequest adminSeq notnull 에러")
	private Long adminSeq;

	@NotNull(message = "NoticeUpdateRequest state notnull 에러")
	@Pattern(regexp = "[NY]", message = "NoticeUpdateRequest state pattern 에러")
	private String state;
}
