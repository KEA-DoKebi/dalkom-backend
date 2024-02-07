package com.dokebi.dalkom.domain.option.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// OP-002에 해당하는 response
public class OptionDetailListResponse {
	private Long prdtOptionSeq;
	private String detail;
}
