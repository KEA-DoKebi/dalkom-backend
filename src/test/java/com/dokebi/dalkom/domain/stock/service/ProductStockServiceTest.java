package com.dokebi.dalkom.domain.stock.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dokebi.dalkom.domain.stock.entity.ProductStock;
import com.dokebi.dalkom.domain.stock.exception.InvalidAmountException;
import com.dokebi.dalkom.domain.stock.exception.NotEnoughStockException;
import com.dokebi.dalkom.domain.stock.repository.ProductStockHistoryRepository;
import com.dokebi.dalkom.domain.stock.repository.ProductStockRepository;

@ExtendWith(MockitoExtension.class)
public class ProductStockServiceTest {

	@InjectMocks
	private ProductStockService productStockService;

	@Mock
	private ProductStockRepository stockRepository;
	@Mock
	private ProductStockHistoryRepository stockHistoryRepository;

	@Test
	@DisplayName("재고 수정 - 정상 작동")
	void updateStockTest() {
		// given
		Long stockSeq = 1L;
		Integer amount = 10;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(6);
		given(stockRepository.findById(stockSeq)).willReturn(Optional.of(productStock));

		// when
		productStockService.updateStock(stockSeq, amount);

		// then
		verify(stockHistoryRepository).save(any());
	}

	@Test
	@DisplayName("재고 수정 - InvalidAmountException 테스트")
	void updateStockExceptionTest() {
		// given
		Long stockSeq = 1L;
		Integer amount = -1;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(10);

		// stockRepository.findById 호출 시 유효한 ProductStock 객체를 반환하도록 설정
		given(stockRepository.findById(stockSeq)).willReturn(Optional.of(productStock));

		// when & then
		assertThatThrownBy(() -> productStockService.updateStock(stockSeq, amount)).isInstanceOf(
			InvalidAmountException.class);
	}

	@Test
	@DisplayName("재고 수정 (옵션) - 정상 작동")
	void updateStockWithOptionTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 5;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(10);
		given(stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)).willReturn(
			Optional.of(productStock));

		// when
		productStockService.updateStock(productSeq, prdtOptionSeq, amountChanged);

		// then
		verify(stockHistoryRepository).save(any());
	}

	@Test
	@DisplayName("재고 수정 (옵션) - NotEnoughStockException 테스트")
	void updateStockWithOptionNotEnoughStockExceptionTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 15;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(10);
		given(stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)).willReturn(
			Optional.of(productStock));

		// when & then
		assertThatThrownBy(
			() -> productStockService.updateStock(productSeq, prdtOptionSeq, amountChanged)).isInstanceOf(
			NotEnoughStockException.class);
	}

	@Test
	@DisplayName("재고 생성 - 정상 작동")
	void createStockTest() {
		// given
		ProductStock stock = new ProductStock(null, null, null);

		// when
		productStockService.createStock(stock);

		// then
		verify(stockRepository).save(stock);
	}

	@Test
	@DisplayName("재고 확인 - 정상 작동")
	void checkStockTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 5;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(10);
		given(stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)).willReturn(
			Optional.of(productStock));

		// when
		productStockService.checkStock(productSeq, prdtOptionSeq, amountChanged);

		// then
		// 예외가 발생하지 않으면 테스트 통과
	}

	@Test
	@DisplayName("재고 확인 - NotEnoughStockException 테스트")
	void checkStockNotEnoughStockExceptionTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 15;
		ProductStock productStock = new ProductStock(null, null, null);
		productStock.setAmount(10);
		given(stockRepository.findPrdtStockByOptionSeq(productSeq, prdtOptionSeq)).willReturn(
			Optional.of(productStock));

		// when & then
		assertThatThrownBy(() -> productStockService.checkStock(productSeq, prdtOptionSeq, amountChanged)).isInstanceOf(
			NotEnoughStockException.class);
	}
}
