// TODO 데이터베이스 연동 하는 법 배운 뒤에 다시 해보기
// package com.dokebi.dalkom.domain.stock.repository;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.Optional;
//
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import com.dokebi.dalkom.domain.option.entity.ProductOption;
// import com.dokebi.dalkom.domain.product.entity.Product;
// import com.dokebi.dalkom.domain.stock.entity.ProductStock;
//
// @ExtendWith(MockitoExtension.class)
// public class ProductStockRepositoryTest {
//
// 	@Mock
// 	private ProductStockRepository productStockRepository;
//
// 	@Test
// 	void findPrdtStockByOptionSeqTest() {
// 		// Given
// 		Product product = new Product(); // 예제상 Product, ProductOption 엔티티의 생성자 및 설정자는 생략
// 		product.setProductSeq(18L);
//
// 		ProductOption productOption = new ProductOption();
// 		productOption.setPrdtOptionSeq(3L);
//
// 		// When
// 		Optional<ProductStock> foundStock = productStockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(
// 			product.getProductSeq(), productOption.getPrdtOptionSeq());
//
// 		// Then
// 		assertThat(foundStock).isNotEmpty();
// 		assertThat(foundStock.get().getAmount()).isEqualTo(10);
//
// 		// When
// 		Optional<ProductStock> notFoundStock = productStockRepository.findPrdtStockByPrdtSeqAndPrdtOptionSeq(-1L, -1L);
//
// 		// Then
// 		assertThat(notFoundStock).isEmpty();
// 	}
// }
