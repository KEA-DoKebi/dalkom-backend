package com.dokebi.dalkom.common.email.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailMessage {

	private String to;
	private String title;

	public EmailMessage(String to, String title) {
		this.to = to;
		this.title = "[DKT 복지몰 달콤샵] " + title;
	}
}
