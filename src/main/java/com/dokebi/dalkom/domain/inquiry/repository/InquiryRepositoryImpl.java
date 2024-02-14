package com.dokebi.dalkom.domain.inquiry.repository;

import static com.dokebi.dalkom.domain.inquiry.entity.QInquiry.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dokebi.dalkom.domain.inquiry.dto.InquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.dto.QInquiryListResponse;
import com.dokebi.dalkom.domain.inquiry.entity.Inquiry;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class InquiryRepositoryImpl implements InquiryRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public InquiryRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<InquiryListResponse> findSearchInquiry(Long categorySeq, String title, Pageable pageable) {
		List<InquiryListResponse> content = queryFactory
			.select(new QInquiryListResponse(
				inquiry.inquirySeq,
				inquiry.title,
				inquiry.createdAt,
				inquiry.answerState

			))
			.from(inquiry)
			.where(titleEq(title), categoryEq(categorySeq))
			.orderBy(inquiry.inquirySeq.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Inquiry> countQuery = queryFactory
			.select(inquiry)
			.from(inquiry)
			.where(
				titleEq(title),
				categoryEq(categorySeq)

			);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private BooleanExpression titleEq(String title) {

		return hasText(title) ? inquiry.title.contains(title) : null;
	}

	private BooleanExpression categoryEq(Long categorySeq) {
		return categorySeq != null ? inquiry.category.categorySeq.eq(categorySeq) : null;
	}

}
