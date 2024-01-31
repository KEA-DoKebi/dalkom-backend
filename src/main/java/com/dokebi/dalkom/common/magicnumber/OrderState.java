package com.dokebi.dalkom.common.magicnumber;

public class OrderState {
	public static final String CONFIRMED = "11"; // 주문확인
	public static final String PREPARING = "12"; // 배송준비
	public static final String SHIPPED = "13"; // 배송시작
	public static final String DELIVERED = "14"; // 배송완료
	public static final String FINALIZED = "15"; // 구매확정
	public static final String CANCELED = "21"; // 주문취소
	public static final String REFUND_CONFIRMED = "31"; // 환불접수
	public static final String RETURNED = "32"; // 반송완료
	public static final String REFUNDED = "33"; // 환불완료

	//"11" : 주문확인, "12" : 배송준비, "13" : 배송시작, "14" : 배송완료, "15" : 구매확정,
	// "21" : 주문취소,
	// "31" : 반품접수, "32" : 반송시작, "33" : 반송완료, "34" : 반품완료,
	// "41" : 환불

}
