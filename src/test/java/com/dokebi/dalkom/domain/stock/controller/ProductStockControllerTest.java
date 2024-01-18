package com.dokebi.dalkom.domain.stock.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
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
	void readStockListByCategoryTest() throws Exception {

		//given
		Long stockSeq = 1L;
		Integer changedAmount = 3;

		ProductStockEditRequest req = new ProductStockEditRequest(changedAmount);

		//when then
		//API 요청을 했을 때 200을 반환하는지 검증
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req)))
			.andExpect(status().isOk());

		//productStockController에서 productStockService editStock을 호출했는지 검증
		verify(productStockService).updateStock(stockSeq, req.getAmount());

	}

	//when,then

}
