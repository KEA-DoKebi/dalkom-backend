package com.dokebi.dalkom.domain.order.factory;

import com.dokebi.dalkom.domain.option.entity.ProductOption;
import com.dokebi.dalkom.domain.order.entity.Order;
import com.dokebi.dalkom.domain.order.entity.OrderDetail;
import com.dokebi.dalkom.domain.product.entity.Product;

public class OrderDetailDtoFactory {
	public class OrderDetailFactory {

		public OrderDetail createOrderDetail(Order order, Product product, ProductOption productOption,
			Integer amount, Integer price) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setProductOption(productOption);
			orderDetail.setAmount(amount);
			orderDetail.setPrice(price);
			return orderDetail;
		}

		// public OrderDetail createSampleOrderDetail() {
		// 	// You can provide default or sample values for testing
		// 	Order order = OrderFactory.createSampleOrder();
		// 	Product product = ProductFactory.createSampleProduct();
		// 	ProductOption productOption = ProductOptionFactory.createSampleProductOption();
		// 	Integer amount = 1;
		// 	Integer price = 100;
		//
		// 	return createOrderDetail(order, product, productOption, amount, price);
		// }

		// You can add more factory methods as needed for different scenarios

	}
}
