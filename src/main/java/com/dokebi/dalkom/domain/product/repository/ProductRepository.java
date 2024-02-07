package com.dokebi.dalkom.domain.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailPage;
import com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse;
import com.dokebi.dalkom.domain.product.dto.ProductCompareDetailDto;
import com.dokebi.dalkom.domain.product.dto.ProductMainResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// PRODUCT-001 - 상위 카테고리로 상품 리스트 조회
	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, AVG(r.rating), COUNT(r)) "
		+ "FROM Product p "
		+ "LEFT JOIN OrderDetail od ON p.productSeq = od.product.productSeq "
		+ "LEFT JOIN Review r ON od.ordrDetailSeq = r.orderDetail.ordrDetailSeq "
		+ "WHERE p.category.parentSeq = :categorySeq and p.state != 'N'"
		+ "GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company "
		+ "ORDER BY p.productSeq DESC ")
	Page<ProductByCategoryResponse> findProductListByCategory(
		@Param("categorySeq") Long categorySeq, Pageable pageable);

	// PRODUCT-002 - 상품 상세 정보 조회
	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductDetailDto(p.category.categorySeq, "
		+ "p.name, p.price, p.info, p.imageUrl, p.company) "
		+ "FROM Product p "
		+ "WHERE p.productSeq = :productSeq ")
	ReadProductDetailDto findProductDetailByProductSeq(@Param("productSeq") Long productSeq);

	// PRODUCT-002 - 상품 상세 정보 조회
	@Query("SELECT pi.imageUrl "
		+ "FROM ProductImage pi "
		+ "WHERE pi.product.productSeq = :productSeq ")
	List<String> findProductImageByProductSeq(@Param("productSeq") Long productSeq);

	// PRODUCT-004 상품 리스트 조회 - 관리자 화면
	@Query(value = "SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.productOption.detail, ps.amount)"
		+ "FROM Product p "
		+ "INNER JOIN ProductStock ps "
		+ "ON p.productSeq = ps.product.productSeq "
		+ "ORDER BY p.productSeq DESC , ps.productOption.prdtOptionSeq DESC ",
		countQuery = "SELECT COUNT(p) FROM Product p ")
	Page<ReadProductResponse> findAdminPageProductList(Pageable pageable);

	// PRODUCT-005 - 하위 카테고리 별 상품 목록 조회
	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductByCategoryDetailPage( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, AVG(r.rating), COUNT(r)) "
		+ "FROM Product p "
		+ "LEFT JOIN  OrderDetail od ON p.productSeq = od.product.productSeq "
		+ "LEFT JOIN Review r ON r.orderDetail.ordrDetailSeq = od.ordrDetailSeq "
		+ "WHERE p.category.categorySeq = :categorySeq and p.state != 'N'"
		+ "GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company "
		+ "ORDER BY p.productSeq DESC ")
	Page<ProductByCategoryDetailPage> findProductListByDetailCategory(
		@Param("categorySeq") Long categorySeq, Pageable pageable);

	// PRODUCT-006 - 전체 카테고리 별 상품 목록 조회 - 메인 화면
	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductMainResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, AVG(r.rating), COUNT(r)) "
		+ "FROM Product p "
		+ "JOIN p.category c "
		+ "LEFT JOIN OrderDetail od ON p.productSeq = od.product.productSeq "
		+ "LEFT JOIN Review r ON od.ordrDetailSeq = r.orderDetail.ordrDetailSeq "
		+ "WHERE c.parentSeq = :categorySeq and p.state != 'N'"
		+ "GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company "
		+ "ORDER BY p.productSeq DESC ")
	Page<ProductMainResponse> findProductListByCategoryAll(@Param("categorySeq") Long categorySeq, Pageable pageable);

	// 이름으로 상품 검색하기
	@Query(value = "SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.productOption.detail, ps.amount)"
		+ "FROM Product p "
		+ "INNER JOIN ProductStock ps "
		+ "ON p.productSeq = ps.product.productSeq "
		+ "WHERE (p.name LIKE CONCAT('%', :name, '%') "
		+ "AND p.state != 'N') "
		+ "ORDER BY p.productSeq DESC , ps.productOption.prdtOptionSeq DESC ",
		countQuery = "SELECT COUNT(p) FROM Product p ")
	Page<ReadProductResponse> findProductListSearchByName(@Param("name") String name, Pageable pageable);

	// 회사이름으로 상품 검색하기
	@Query(value = "SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.productOption.detail, ps.amount)"
		+ "FROM Product p "
		+ "INNER JOIN ProductStock ps "
		+ "ON p.productSeq = ps.product.productSeq "
		+ "WHERE (p.company LIKE CONCAT('%', :company, '%') "
		+ "AND p.state != 'N') "
		+ "ORDER BY p.productSeq DESC , ps.productOption.prdtOptionSeq DESC ",
		countQuery = "SELECT COUNT(p) FROM Product p ")
	Page<ReadProductResponse> findProductListSearchByCompany(@Param("company") String company, Pageable pageable);

	//  상품 검색하기 전체
	@Query(value = "SELECT NEW com.dokebi.dalkom.domain.product.dto.ReadProductResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, ps.productOption.detail, ps.amount)"
		+ "FROM Product p "
		+ "INNER JOIN ProductStock ps "
		+ "ON p.productSeq = ps.product.productSeq "
		+ "WHERE ( p.state != 'N') "
		+ "ORDER BY p.productSeq DESC , ps.productOption.prdtOptionSeq DESC ",
		countQuery = "SELECT COUNT(p) FROM Product p ")
	Page<ReadProductResponse> findProductListSearch(Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductMainResponse( "
		+ "p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company, AVG(r.rating), COUNT(r)) "
		+ "FROM Product p "
		+ "LEFT JOIN OrderDetail od ON p.productSeq = od.product.productSeq "
		+ "LEFT JOIN Review r ON od.ordrDetailSeq = r.orderDetail.ordrDetailSeq "
		+ "WHERE p.state != 'N' AND p.name LIKE CONCAT('%', :name, '%') "
		+ "GROUP BY p.productSeq, p.name, p.price, p.state, p.imageUrl, p.company "
		+ "ORDER BY p.productSeq DESC ")
	Page<ProductMainResponse> findProductListSearchUserByName(
		@Param("name") String name, Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.product.dto.ProductCompareDetailDto( "
		+ "p.name, p.imageUrl, p.price) "
		+ "FROM Product p WHERE p.productSeq = :productSeq")
	Optional<ProductCompareDetailDto> readProductCompareDetailByProductSeq(@Param("productSeq") Long productSeq);

	// 다른 Domain Service에서 사용하도록 하는 메소드
	Optional<Product> findProductByProductSeq(Long productSeq);
}
