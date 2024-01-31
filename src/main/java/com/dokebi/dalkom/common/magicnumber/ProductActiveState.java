package com.dokebi.dalkom.common.magicnumber;

import lombok.Getter;

@Getter
public enum ProductActiveState {

	// 판매중, 사용자에게 공개됨
	ACTIVE("Y", "판매중"),

	// 판매 안함, 사용자에게 공개됨 (품절)
	SOLDOUT("S", "품절"),

	// 판매 안함, 사용자에게 공개되지 않음
	INACTIVE("N", "판매중지");

	private final String state;
	private final String name;

	ProductActiveState(String state, String message) {
		this.state = state;
		this.name = message;
	}

}
