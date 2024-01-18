package com.dokebi.dalkom.domain.stock.service;

import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.repository.ProductStockHistoryRepository;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

@ExtendWith(MockitoExtension.class)
public class ProductStockServiceTest {

	@InjectMocks
	ProductStockService productStockService;

	@Mock
	ProductStockRepository stockRepository;
	@Mock
	ProductStockHistoryRepository stockHistoryRepository;

	@Test
	@DisplayName("재고를 수정한다.")
	void editStockTest() {
		//given
		//editStock 파라미터 설정
		Long stockSeq = 1L;
		Integer amount = 10;

		//레포지토리에서 리턴 받는 ProductStock 객체 설정
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setPrdtStockSeq(1L);
		productStock.setAmount(6);

		given(stockRepository.findByPrdtStockSeq(any())).willReturn(productStock);

		//when
		// productStockService.editStock를 실행한다.
		productStockService.updateStock(stockSeq, amount);

		// then
		// productStock
		verify(stockHistoryRepository).save(any());

	}

	@Test
	@DisplayName("재고가 0보다 작으면 Exception이 발생한다.")
	void editStockExceptionTest() {
		//given
		//editStock 파라미터 설정
		Long stockSeq = 1L;
		Integer amount = -1;

		// when then
		// productStock
		Assertions.assertThatThrownBy(() -> productStockService.updateStock(stockSeq, amount)).isInstanceOf(
			InvalidAmountException.class);

	}

}
