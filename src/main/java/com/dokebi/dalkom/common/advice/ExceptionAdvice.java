package com.dokebi.dalkom.common.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dokebi.dalkom.common.email.exception.MailSendFailException;
import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;
import com.dokebi.dalkom.domain.cart.exception.OrderCartNotFoundException;
import com.dokebi.dalkom.domain.category.exception.CategoryNotFoundException;
import com.dokebi.dalkom.domain.chat.exception.GptResponseFailException;
import com.dokebi.dalkom.domain.inquiry.exception.FaqNotFoundException;
import com.dokebi.dalkom.domain.inquiry.exception.InquiryNotFoundException;
import com.dokebi.dalkom.domain.jira.exception.MissingJiraRequestHeaderException;
import com.dokebi.dalkom.domain.mileage.exception.MileageAlreadyApplyException;
import com.dokebi.dalkom.domain.mileage.exception.MileageApplyNotFoundException;
import com.dokebi.dalkom.domain.mileage.exception.MileageLackException;
import com.dokebi.dalkom.domain.notice.exception.NoticeNotFoundException;
import com.dokebi.dalkom.domain.option.exception.ProductOptionNotFoundException;
import com.dokebi.dalkom.domain.order.exception.InvalidOrderStateException;
import com.dokebi.dalkom.domain.order.exception.OrderDetailNotFoundException;
import com.dokebi.dalkom.domain.order.exception.OrderNotFoundException;
import com.dokebi.dalkom.domain.order.exception.PasswordNotValidException;
import com.dokebi.dalkom.domain.product.exception.InvalidProductInputException;
import com.dokebi.dalkom.domain.product.exception.ProductNotFoundException;
import com.dokebi.dalkom.domain.review.exception.ReviewAlreadyExistsException;
import com.dokebi.dalkom.domain.review.exception.ReviewNotFoundException;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.exception.NotEnoughStockException;
import com.dokebi.dalkom.domain.stock.exception.ProductStockNotFoundException;
import com.dokebi.dalkom.domain.user.exception.EmployeeNotFoundException;
import com.dokebi.dalkom.domain.user.exception.InvalidJoinedAtException;
import com.dokebi.dalkom.domain.user.exception.LoginFailureException;
import com.dokebi.dalkom.domain.user.exception.UserEmailAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNicknameAlreadyExistsException;
import com.dokebi.dalkom.domain.user.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response memberNotFoundException() {
		return Response.failure(-1001, "회원 정보를 찾을 수 없습니다.");
	}

	@ExceptionHandler(EmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response employeeNotFoundException() {
		return Response.failure(-1002, "임직원 정보를 찾을 수 없습니다.");
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public Response missingRequestHeaderException(MissingRequestHeaderException e) {
		return Response.failure(-1003, e.getHeaderName() + "요청 헤더가 누락되었습니다.");
	}

	@ExceptionHandler(MissingJiraRequestHeaderException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public Response missingJiraRequestHeaderException() {
		return Response.failure(-1004, "Jira 요청 헤더가 누락되었습니다.");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public Response methodArgumentNotValidException(MethodArgumentNotValidException e) {
		BindingResult result = e.getBindingResult();
		List<FieldError> fieldErrorList = result.getFieldErrors();

		if (!fieldErrorList.isEmpty()) {
			return Response.failure(-1005, fieldErrorList.get(0).getDefaultMessage());
		} else {
			return Response.failure(-1005, "잘못된 값이 들어왔습니다.");
		}

	}

	// // 사용자 + 로그인

	@ExceptionHandler(LoginFailureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	public Response loginFailureException() {
		return Response.failure(-1100, "로그인에 실패하였습니다.");
	}

	@ExceptionHandler(UserEmailAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response userEmailAlreadyExistsException(UserEmailAlreadyExistsException e) { // 4
		return Response.failure(-1101, e.getMessage() + "은 중복된 이메일 입니다.");
	}

	@ExceptionHandler(UserNicknameAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response userNicknameAlreadyExistsException(UserNicknameAlreadyExistsException e) {
		return Response.failure(-1102, e.getMessage() + "은 중복된 닉네임 입니다.");
	}

	@ExceptionHandler(InvalidJoinedAtException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response invalidJoinedAtException() {
		return Response.failure(-1103, "잘못된 입사일입니다.");
	}

	// 상품
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response productNotFoundException() {
		return Response.failure(-1200, "상품을 찾을 수 없습니다.");
	}

	@ExceptionHandler(InvalidProductInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public Response invalidProductInputException() {
		return Response.failure(-1201, "입력된 상품 정보가 잘못되었습니다.");
	}

	// 주문
	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response orderNotFoundException() {
		return Response.failure(-1300, "주문을 찾을수 없습니다.");
	}

	@ExceptionHandler(InvalidOrderStateException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response invalidOrderStateException() {
		return Response.failure(-1301, "잘못된 주문입니다.");
	}

	@ExceptionHandler(PasswordNotValidException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
	public Response passwordNotValidException() {
		return Response.failure(-1302, "인증에 실패했습니다.");
	}

	// 마일리지 (1400)
	@ExceptionHandler(MileageLackException.class)
	@ResponseStatus(HttpStatus.PAYMENT_REQUIRED) // 402
	public Response mileageLackException() {
		return Response.failure(-1400, "마일리지가 부족합니다.");
	}

	@ExceptionHandler(MileageApplyNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response mileageApplyNotFoundException() {
		return Response.failure(-1401, "찾고자 하는 마일리지 신청 정보를 찾을 수 없습니다.");
	}

	@ExceptionHandler(MileageAlreadyApplyException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response mileageAlreadyApplyException() {
		return Response.failure(-1402, "이미 진행중인 마일리지 신청 내역이 존재합니다.");
	}

	// 카트
	@ExceptionHandler(OrderCartNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response orderCartEmptyResultDataAccessException() {
		return Response.failure(-1500, "장바구니 정보를 찾을 수 없습니다.");
	}

	// 재고
	@ExceptionHandler(InvalidAmountException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public Response invalidAmountException() {
		return Response.failure(-1600, "잘못된 입력값입니다.");
	}

	@ExceptionHandler(NotEnoughStockException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN) // 403
	public Response notEnoughStockException() {
		return Response.failure(-1601, "재고가 부족합니다.");
	}

	@ExceptionHandler(ProductStockNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response productStockNotFoundException() {
		return Response.failure(-1602, "재고를 찾을 수 없습니다.");
	}

	// 옵션
	@ExceptionHandler(ProductOptionNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response productOptionNotFoundException() {
		return Response.failure(-1700, "옵션을 찾을 수 없습니다.");
	}

	// 리뷰
	@ExceptionHandler(ReviewNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response reviewNotFoundException() {
		return Response.failure(-1800, "요청하신 리뷰를 찾을 수 없습니다.");
	}

	@ExceptionHandler(OrderDetailNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response orderDetailNotFoundException() {
		return Response.failure(-1801, "요청하신 주문상세를 찾을 수 없습니다.");
	}

	@ExceptionHandler(ReviewAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public Response reviewAlreadyExistsException() {
		return Response.failure(-1802, "이미 리뷰가 존재합니다.");
	}

	// 카테고리
	@ExceptionHandler(CategoryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response CategoryNotFoundException() {
		return Response.failure(-1900, "카테고리를 찾을 수 없습니다.");
	}

	// 관리자
	@ExceptionHandler(AdminNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response adminNotFoundException() {
		return Response.failure(-2000, "해당 관리자를 찾을 수 없습니다.");
	}

	// 문의
	@ExceptionHandler(InquiryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response inquiryNotFoundException() {
		return Response.failure(-2100, "해당 문의를 찾을 수 없습니다.");
	}

	@ExceptionHandler(FaqNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response faqNotFoundException() {
		return Response.failure(-2101, "해당 FAQ를 찾을 수 없습니다.");
	}

	// 공지사항
	@ExceptionHandler(NoticeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public Response noticeNotFoundException() {
		return Response.failure(-2200, "해당 공지를 찾을 수 없습니다.");
	}

	// ChatGPT
	@ExceptionHandler(GptResponseFailException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) //503
	public Response gptNoResponseException(GptResponseFailException e) {
		return Response.failure(-2300, e.getMessage());
	}

	// Email
	@ExceptionHandler(MailSendFailException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) // 503
	public Response mailSendException(MailSendFailException e) {
		return Response.failure(-2400, e.getMessage());
	}
}
