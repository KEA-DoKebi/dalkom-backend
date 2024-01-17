package com.dokebi.dalkom.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.cart.exception.OrderCartEmptyResultDataAccessException;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

	// 공통 권한

	// @ExceptionHandler(Exception.class)
	// @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	// public Response exception(Exception e) {
	//     //구체적인 에러 보여주기
	//     log.info("e= {}",e.getMessage());
	//     return Response.failure(-1000,"오류가 발생하였습니다.");
	//
	// }
	// //401 에러
	// @ExceptionHandler(AuthenticationEntryPointException.class)
	// @ResponseStatus(HttpStatus.UNAUTHORIZED)
	// public Response authenticationEntryPoint(){
	//     return Response.failure(-1001,"인증되지 않은 사용자입니다.");
	// }
	//
	// //403 에러
	// @ExceptionHandler(AccessDeniedException.class)
	// @ResponseStatus(HttpStatus.FORBIDDEN)
	// public Response accessDeniedException(){
	//     return Response.failure(-1002,"접근이 거부되었습니다");
	// }
	//
	// @ExceptionHandler(BindException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST) //400
	// public Response bindException(BindException e){
	//     return Response.failure(-1003,e.getBindingResult().getFieldError().getDefaultMessage());
	// }

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)//404
	public Response memberNotFoundException() {
		return Response.failure(1001, "요청한 회원을 찾을 수 없습니다.");
	}
	//
	// @ExceptionHandler (RoleNotFoundException.class)
	// @ResponseStatus (HttpStatus.NOT_FOUND)//404
	// public Response roleNotFoundException(){
	//     return Response.failure(-1008,"요청한 권한 등급을 찾을 수 없습니다. ");
	// }
	//
	// @ExceptionHandler(MissingRequestHeaderException.class)
	// @ResponseStatus(HttpStatus.BAD_REQUEST)
	// public Response missingRequestHeaderException(MissingRequestHeaderException e) {
	//     return Response.failure(-1009,e.getHeaderName()+"요청 헤더가 누락되었습니다.");

	// 사용자 + 로그인

	// @ExceptionHandler(LoginFailureException.class)
	// @ResponseStatus(HttpStatus.UNAUTHORIZED)
	// public Response loginFailureException(){
	//     return Response.failure(-1004,"로그인에 실패하였습니다.");
	// }
	//
	//
	// @ExceptionHandler(UserEmailAlreadyExistsException.class)
	// @ResponseStatus(HttpStatus.CONFLICT)
	// public Response memberEmailAlreadyExistsException(UserEmailAlreadyExistsException e) { // 4
	//     return Response.failure(-1005, e.getMessage() + "은 중복된 이메일 입니다.");
	// }
	//
	//
	// @ExceptionHandler (UserNicknameAlreadyExistsException.class)
	// @ResponseStatus (HttpStatus.CONFLICT)//401
	// public Response memberNicknameAlreadyExistsException(UserNicknameAlreadyExistsException e){
	//     return Response.failure(-1006,e.getMessage()+"은 중복된 닉네임 입니다.");
	// }

	// 상품
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Response productNotFoundException() {
		return Response.failure(1200, "요청한 상품을 찾을 수 없습니다.");
	}

	// 주문

	// 마일리지

	// 카트
	@ExceptionHandler(OrderCartEmptyResultDataAccessException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Response orderCartEmptyResultDataAccessException() {
		return Response.failure(1500, "삭제 혹은 수정하고자 하는 장바구니 정보를 찾을 수 없습니다.");
	}

	// 재고

	// 카테고리

	// 관리자 + 로그인

	// 문의

	// 공지사항

}