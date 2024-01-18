package com.dokebi.dalkom.domain.stock.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.stock.entity.ProductStock;

@DataJpaTest
public class ProductStockRepositoryTest {

	@Autowired
	private ProductStockRepository productStockRepository;

	@Test
	void findPrdtStockByOptionSeqTest() {
		// Given
		Product product = new Product(); // 예제상 Product, ProductOption 엔티티의 생성자 및 설정자는 생략
		ProductOption productOption = new ProductOption();
		ProductStock stock = new ProductStock(product, productOption, 10);

		productStockRepository.save(stock);

		// When
		Optional<ProductStock> foundStock = productStockRepository.findPrdtStockByOptionSeq(
			product.getProductSeq(), productOption.getPrdtOptionSeq());

		// Then
		assertThat(foundStock).isNotEmpty();
		assertThat(foundStock.get().getAmount()).isEqualTo(10);

		// When
		Optional<ProductStock> notFoundStock = productStockRepository.findPrdtStockByOptionSeq(-1L, -1L);

		// Then
		assertThat(notFoundStock).isEmpty();
	}
}
