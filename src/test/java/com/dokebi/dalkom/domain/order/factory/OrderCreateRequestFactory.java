// package com.dokebi.dalkom.domain.order.factory;
//
// import java.util.Arrays;
// import java.util.Collections;
//
// import com.dokebi.dalkom.domain.order.dto.OrderCreateRequest;
//
// public class OrderCreateRequestFactory {
// 	public static OrderCreateRequest createOrderCreateRequest() {
// 		return new OrderCreateRequest(
// 			ReceiverInfoRequestFactory.createReceiverInfoRequest(),  // receiverInfoRequest
// 			Arrays.asList(
// 				OrderProductRequestFactory.createOrderProductRequest(),  // orderProductRequestList[0]
// 				OrderProductRequestFactory.createOrderProductRequest()   // orderProductRequestList[1]
// 			)
// 		);
// 	}
//
// 	public static OrderCreateRequest createOrderCreateRequestWithUserSeqNull() {
// 		return new OrderCreateRequest(
// 			null,  // receiverInfoRequest
// 			Collections.singletonList(
// 				OrderProductRequestFactory.createOrderProductRequest()
// 			)
// 		);
// 	}
//
// }
