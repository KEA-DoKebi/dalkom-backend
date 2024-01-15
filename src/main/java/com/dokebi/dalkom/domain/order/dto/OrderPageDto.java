package com.dokebi.dalkom.domain.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageDto {
	//클래스를 generic 타입으로 가지는 List 타입의 변수를 선언
	private List<OrderPageDetailDto> orders;
}
