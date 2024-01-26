package com.dokebi.dalkom.domain.stock.controller;

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
	@DisplayName("정상 작동 테스트")
	void readStockListByCategoryTest() throws Exception {

		// given
		Long stockSeq = 1L;
		Integer changedAmount = 3;

		ProductStockEditRequest req = new ProductStockEditRequest(changedAmount);

		// when
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req)))
			.andExpect(status().isOk()); // then (API 요청을 했을 때 200을 반환하는지 검증)

		//productStockController에서 productStockService updateStock을 호출했는지 검증
		verify(productStockService).updateStockByStockSeq(stockSeq, req.getAmount());
	}

	@Test
	@DisplayName("Valid 테스트")
	void readStockListByCategoryValidTest() throws Exception {

		//given (changedAmount 변수 값을 범위에서 벗어나게 설정)
		Long stockSeq = 1L;
		Integer changedAmount = -3;

		ProductStockEditRequest req = new ProductStockEditRequest(changedAmount);

		// when
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req)))
			.andExpect(status().isBadRequest()); // thenAPI 요청을 했을 때 400을 반환하는지 검증
	}
}
