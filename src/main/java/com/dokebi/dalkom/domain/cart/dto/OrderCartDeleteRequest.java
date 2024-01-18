package com.dokebi.dalkom.domain.cart.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDeleteRequest {

	@NotEmpty
	List<Long> orderCartSeqList;
}
