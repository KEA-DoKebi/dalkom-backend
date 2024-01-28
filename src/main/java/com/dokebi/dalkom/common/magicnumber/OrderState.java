package com.dokebi.dalkom.common.magicnumber;

public class OrderState {
	public static String Before_Authorize = "10"; // 비밀전호 인증 전
	public static String CONFIRMED = "11"; // 주문확인
	public static String PREPARING = "12"; // 배송준비
	public static String SHIPPED = "13"; // 배송시작
	public static String DELIVERED = "14"; // 배송완료
	public static String FINALIZED = "15"; // 구매확정
	public static String CANCELED = "21"; // 주문취소
	public static String REFUND_CONFIRMED = "31"; // 환불접수
	public static String RETURNING = "32"; // 반송시작
	public static String RETURNED = "33"; // 반송완료
	public static String REFUNDED = "34"; // 환불완료

}
