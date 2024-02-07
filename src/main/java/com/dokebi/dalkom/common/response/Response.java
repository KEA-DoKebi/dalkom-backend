package com.dokebi.dalkom.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

//null 값을 가지는 필드는, JSON 응답에 포함되지 않도록 합니다.
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
//응답 객체를 JSON으로 변환하려면 getter가 필요합니다
@Getter
public class Response {
	private boolean success;
	private int code;
	private Result result;

	public static Response success() { // 4
		return new Response(true, 200, null);
	}

	//성공했을 때는 응답 데이터도 반환해줍니다.
	public static <T> Response success(T data) { // 5
		return new Response(true, 200, new Success<>(data));
	}

	//실패했을 때는 실패 메시지도 반환해줍니다.
	public static Response failure(int code, String msg) { // 6
		return new Response(false, code, new Failure(msg));
	}
}
