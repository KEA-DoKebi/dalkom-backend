package com.dokebi.dalkom.domain.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.order.dto.OrderDetailDto;
import com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	Optional<OrderDetail> findOrderDetailByOrdrDetailSeq(Long ordrDetailSeq);

	@Query("SELECT od FROM OrderDetail od WHERE od.order.ordrSeq =:orderSeq")
	List<OrderDetail> findOrderDetailListByOrderSeq(@Param("orderSeq") Long orderSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderDetailDto( "
		+ "od.product.name, od.product.imageUrl, od.productOption.prdtOptionSeq, od.productOption.detail, "
		+ "od.order.createdAt, od.order.ordrSeq, od.amount, od.price, od.order.orderState) "
		+ "FROM  OrderDetail od WHERE od.order.ordrSeq =:orderSeq")
	List<OrderDetailDto> findOrderDetailDtoListByOrderSeq(@Param("orderSeq") Long orderSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.order.dto.OrderDetailSimpleResponse( "
		+ "od.product.name, od.product.imageUrl, od.productOption.prdtOptionSeq, od.productOption.detail) "
		+ "FROM  OrderDetail od WHERE od.ordrDetailSeq =:ordrDetailSeq")
	Optional<OrderDetailSimpleResponse> readOrderDetailSimpleResponseByordrDetailSeq(
		@Param("ordrDetailSeq") Long ordrDetailSeq);

}
