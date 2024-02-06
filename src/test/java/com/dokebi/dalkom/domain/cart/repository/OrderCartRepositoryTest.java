package com.dokebi.dalkom.domain.cart.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.dokebi.dalkom.domain.cart.dto.OrderCartReadResponse;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderCartRepositoryTest {
	@Autowired
	OrderCartRepository orderCartRepository;
	@Autowired
	TestEntityManager entityManager;

	@Test
	@DisplayName("findOrderCartList 테스트")
	void findOrderCartListTest() {
		// Given
		Long userSeq = 1L;
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<OrderCartReadResponse> orderCartListPage = orderCartRepository.findOrderCartList(userSeq, pageable);

		// Then
		assertNotNull(orderCartListPage);
		assertFalse(orderCartListPage.isEmpty());
	}
}
