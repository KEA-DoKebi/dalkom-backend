package com.dokebi.dalkom.domain.stock.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.dokebi.dalkom.domain.stock.dto.ProductStockEditRequest;
import com.dokebi.dalkom.domain.stock.service.ProductStockService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ProductStockControllerTest {
	@InjectMocks
	ProductStockController productStockController;

	@Mock
	ProductStockService productStockService;
	MockMvc mockMvc;
	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.standaloneSetup(productStockController).build();
	}

	@Test
	@DisplayName("재고 업데이트 정상 작동 테스트")
	void updateStockByStockSeqTest() throws Exception {
		// given (재고 ID와 변경될 수량을 설정)
		Long stockSeq = 1L;
		Integer changedAmount = 3;

		ProductStockEditRequest request = new ProductStockEditRequest(changedAmount);

		// when (API 호출)
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk()); // then (API 요청이 성공적으로 처리되었는지 검증)

		// then (productStockService의 updateStockByStockSeq 메서드가 호출되었는지 검증)
		verify(productStockService).updateStockByStockSeq(stockSeq, changedAmount);
	}

	@Test
	@DisplayName("재고 업데이트 유효성 검사 테스트")
	void updateStockByStockSeqValidTest() throws Exception {
		// given (유효하지 않은 변경 수량 설정)
		Long stockSeq = 1L;
		Integer changedAmount = -3;

		ProductStockEditRequest request = new ProductStockEditRequest(changedAmount);

		// when (API 호출)
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
			.andExpect(result -> assertTrue(
				result.getResolvedException() instanceof MethodArgumentNotValidException)) // then (MethodArgumentNotValidException 발생 검증)
			.andExpect(status().isBadRequest()); // 추가적으로, 상태 코드 400(Bad Request) 검증

	}
}
