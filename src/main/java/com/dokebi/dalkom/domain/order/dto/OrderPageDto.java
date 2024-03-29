package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPageDto {

	// class를 generic type으로 가지는 List 타입의 변수를 선언
	private List<OrderPageDetailDto> orderList;
}
