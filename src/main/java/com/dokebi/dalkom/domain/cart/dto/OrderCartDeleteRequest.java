package com.dokebi.dalkom.domain.cart.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCartDeleteRequest {
	List<Long> orderCartSeqList;
}
