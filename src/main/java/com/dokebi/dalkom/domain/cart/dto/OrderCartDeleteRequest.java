package com.dokebi.dalkom.domain.cart.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDeleteRequest {
	List<Long> orderCartSeqList;
}
