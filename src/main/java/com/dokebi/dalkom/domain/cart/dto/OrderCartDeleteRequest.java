package com.dokebi.dalkom.domain.cart.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCartDeleteRequest {
	@NotEmpty(message = "OrderCartDeleteRequest orderCartSeqList NotEmpty 에러")
	List<Long> orderCartSeqList;
}
