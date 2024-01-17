package com.dokebi.dalkom.domain.stock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dokebi.dalkom.domain.stock.dto.ProductStockEditRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class stockIntegrationTest {

	@Autowired
	WebApplicationContext context;
	@Autowired MockMvc mockMvc;

	@BeforeEach
	void beforeEach() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@DisplayName("기존 재고를 5로 수정한다")
	void editStockTest() throws Exception {
		//given
		Long stockSeq = 1L;
		Integer changedAmount = 3;

		ProductStockEditRequest req = new ProductStockEditRequest(changedAmount);
		ObjectMapper objectMapper = new ObjectMapper();

		//when,then
		mockMvc.perform(
				put("/api/stock/{stockSeq}", stockSeq)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(req)))
			.andExpect(status().isOk());
	}


}
