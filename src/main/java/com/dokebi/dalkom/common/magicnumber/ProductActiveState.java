package com.dokebi.dalkom.common.magicnumber;

import lombok.Generated;
import lombok.Getter;

@Getter
@Generated
public enum ProductActiveState {

	// 판매중, 사용자에게 공개됨
	ACTIVE("Y", "판매중"),

	// 판매 안함, 사용자에게 공개됨 (품절)
	SOLDOUT("S", "품절"),

	// 판매 안함, 사용자에게 공개되지 않음
	INACTIVE("N", "판매중지");

	private final String state;
	private final String name;

	ProductActiveState(String state, String name) {
		this.state = state;
		this.name = name;
	}

	public static String getNameByState(String state) {
		for (ProductActiveState productActiveState : ProductActiveState.values()) {
			if (productActiveState.state.equals(state)) {
				return productActiveState.name;
			}
		}
		return null; // 또는 기본값
	}
}
