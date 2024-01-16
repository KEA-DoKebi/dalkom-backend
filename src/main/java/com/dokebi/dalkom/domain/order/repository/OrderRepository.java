package com.dokebi.dalkom.domain.order.repository;

import java.util.List;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.order.dto.OrderPageDetailDto;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.dokebi.dalkom.domain.user.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
<<<<<<< Updated upstream
	//상품정보 조회
=======
	// 유저별 주문조회
	List<Order> findByUser_UserSeq(Long userSeq );
	//주문별 주문조회
	Order findByOrdrSeq(Long orderSeq);

	//전체 주문조회
	List<Order> findAll();






>>>>>>> Stashed changes
}
