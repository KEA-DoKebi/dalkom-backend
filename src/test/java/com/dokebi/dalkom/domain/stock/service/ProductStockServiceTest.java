package com.dokebi.dalkom.domain.stock.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
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
	@DisplayName("재고 수정-정상작동")
	void updateStockTest() {
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
		// productStockService.updateStock을 실행한다.
		productStockService.updateStock(stockSeq, amount);

		// then
		// productStock
		verify(stockHistoryRepository).save(any());

	}

	@Test
	@DisplayName("재고 수정-InvalidAmountException 테스트")
	void updateStockExceptionTest() {
		//given
		//editStock 파라미터 설정
		Long stockSeq = 1L;
		Integer amount = -1;

		// when then
		// productStock
		Assertions.assertThatThrownBy(() -> productStockService.updateStock(stockSeq, amount)).isInstanceOf(
			InvalidAmountException.class);

	}

	@Test
	@DisplayName("재고 생성-정상 작동")
	void createStockTest() {
		//given
		//editStock 파라미터 설정
		Long productSeq = 3L;
		Long prdtOptionSwq = 8L;
		Integer amountChanged = 8;

		//ProductStock, ProductOption 객체 생성
		ProductOption productOption = new ProductOption(null, null, null, null);
		productOption.setPrdtOptionSeq(prdtOptionSwq);
		productOption.setOptionCode("OP10");
		productOption.setName("테스트용 상품");
		productOption.setDetail("5XL");

		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setPrdtStockSeq(productSeq);
		productStock.setProductOption(productOption);
		productStock.setAmount(6);

		given(stockRepository.findPrdtStockByOptionSeq(anyLong(), anyLong())).willReturn(Optional.of(productStock));

		//when
		// productStockService.updateStock을 실행한다.
		// productStockService.updateStock(stockSeq, amount);

		// then
		// productStock
		verify(stockHistoryRepository).save(any());

	}

}
