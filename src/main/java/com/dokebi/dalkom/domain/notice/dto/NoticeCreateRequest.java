package com.dokebi.dalkom.domain.notice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeCreateRequest {

	@NotNull(message = "NoticeCreateRequest title NotNull 에러")
	@NotBlank(message = "NoticeCreateRequest title NotBlank 에러")
	private String title;

	@NotNull(message = "NoticeCreateRequest content NotNull 에러")
	@NotBlank(message = "NoticeCreateRequest content NotBlank 에러")
	private String content;

	@NotNull(message = "NoticeCreateRequest state NotNull 에러")
	@Pattern(regexp = "[NY]", message = "NoticeCreateRequest state Pattern 에러")
	private String state;

	// @NotNull(message = "NoticeCreateRequest adminSeq notnull 에러")
	// private Long adminSeq;
}
