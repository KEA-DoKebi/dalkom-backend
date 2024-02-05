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

import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.product.service.ProductService;
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

	@Mock
	private ProductService productService;

	@Test
	@DisplayName("재고 수정 - 정상 작동")
	void updateStockByStockSeqTest() {
		// given
		Long stockSeq = 1L;
		Long productSeq = 2L;
		Integer amount = 10;
		ProductStock productStock = ProductStock.createProductStock();
		Product product = new Product(productSeq);
		productStock.setProduct(product);
		productStock.setAmount(6);

		given(stockRepository.findById(stockSeq)).willReturn(Optional.of(productStock));

		// when
		productStockService.updateStockByStockSeq(stockSeq, amount);

		// then
		verify(stockHistoryRepository).save(any());
		assertThat(productStock.getAmount()).isEqualTo(amount);
	}

	@Test
	@DisplayName("재고 수정 - InvalidAmountException 테스트")
	void updateStockByStockSeqInvalidAmountExceptionTest() {
		// given
		Long stockSeq = 1L;
		Long productSeq = 2L;
		Integer amount = -1;
		ProductStock productStock = ProductStock.createProductStock();
		Product product = new Product(productSeq);
		productStock.setProduct(product);

		given(stockRepository.findById(stockSeq)).willReturn(Optional.of(productStock));

		// when & then
		assertThatThrownBy(() -> productStockService.updateStockByStockSeq(stockSeq, amount))
			.isInstanceOf(InvalidAmountException.class);
	}

	@Test
	@DisplayName("재고 수정 (옵션) - 정상 작동")
	void updateStockByProductSeqAndOptionSeqTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 5;
		ProductStock productStock = ProductStock.createProductStock();
		productStock.setProduct(new Product(productSeq));
		ProductOption productOption = ProductOption.createProductOption();
		productOption.setPrdtOptionSeq(prdtOptionSeq);
		productStock.setProductOption(productOption);
		productStock.setAmount(10);

		given(stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq))
			.willReturn(Optional.of(productStock));

		// when
		productStockService.updateStockByProductSeqAndOptionSeq(productSeq, prdtOptionSeq, amountChanged);

		// then
		verify(stockHistoryRepository).save(any());
		assertThat(productStock.getAmount()).isEqualTo(10 - amountChanged);
	}

	@Test
	@DisplayName("재고 수정 (옵션) - NotEnoughStockException 테스트")
	void updateStockByProductSeqAndOptionSeqNotEnoughStockExceptionTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 15;
		ProductStock productStock = ProductStock.createProductStock();
		productStock.setAmount(10);

		given(stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq))
			.willReturn(Optional.of(productStock));

		// when & then
		assertThatThrownBy(
			() -> productStockService.updateStockByProductSeqAndOptionSeq(productSeq, prdtOptionSeq, amountChanged))
			.isInstanceOf(NotEnoughStockException.class);
	}

	@Test
	@DisplayName("재고 생성 - 정상 작동")
	void createStockTest() {
		// given
		ProductStock stock = ProductStock.createProductStock();

		// when
		productStockService.createStock(stock);

		// then
		verify(stockRepository).save(stock);
	}

	@Test
	@DisplayName("재고 확인 - NotEnoughStockException 테스트")
	void checkStockNotEnoughStockExceptionTest() {
		// given
		Long productSeq = 1L;
		Long prdtOptionSeq = 1L;
		Integer amountChanged = 15;
		ProductStock productStock = ProductStock.createProductStock();
		productStock.setAmount(10);

		given(stockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(productSeq, prdtOptionSeq))
			.willReturn(Optional.of(productStock));

		// when & then
		assertThatThrownBy(() -> productStockService.checkStock(productSeq, prdtOptionSeq, amountChanged))
			.isInstanceOf(NotEnoughStockException.class);
	}
}
