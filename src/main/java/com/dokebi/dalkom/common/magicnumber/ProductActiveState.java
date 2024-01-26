package com.dokebi.dalkom.common.magicnumber;

public class ProductActiveState {

	// 판매중, 사용자에게 공개됨
	public static final String ACTIVE = "Y";

	// 판매 안함, 사용자에게 공개됨 (품절)
	public static final String SOLDOUT = "S";

	// 판매 안함, 사용자에게 공개되지 않음
	public static final String INACTIVE = "N";
}
