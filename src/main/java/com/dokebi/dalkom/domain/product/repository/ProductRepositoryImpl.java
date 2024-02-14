package com.dokebi.dalkom.domain.product.repository;

import static com.dokebi.dalkom.domain.product.entity.QProduct.*;
import static com.dokebi.dalkom.domain.stock.entity.QProductStock.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dokebi.dalkom.domain.product.dto.ProductSearchCondition;
import com.dokebi.dalkom.domain.product.dto.QReadProductResponse;
import com.dokebi.dalkom.domain.product.dto.ReadProductResponse;
import com.dokebi.dalkom.domain.product.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public ProductRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<ReadProductResponse> searchProduct(ProductSearchCondition condition, Pageable pageable) {
		List<ReadProductResponse> content = queryFactory
			.select(new QReadProductResponse(
				product.productSeq,
				product.name,
				product.price,
				product.state,
				product.imageUrl,
				product.company,
				productStock.productOption.detail,
				productStock.amount
			))
			.from(product)
			.innerJoin(productStock).on(product.productSeq.eq(productStock.product.productSeq))
			.where(nameEq(condition.getName()), companyEq(condition.getCompany()))
			.orderBy(product.productSeq.desc(), productStock.productOption.prdtOptionSeq.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Product> countQuery = queryFactory
			.select(product)
			.from(product)
			.where(
				nameEq(condition.getName()),
				companyEq(condition.getCompany())
			);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private BooleanExpression nameEq(String name) {
		return hasText(name) ? product.name.contains(name) : null;
	}

	private BooleanExpression companyEq(String company) {
		return hasText(company) ? product.company.contains(company) : null;
	}
}
