package com.dokebi.dalkom.domain.admin.repository;

import static com.dokebi.dalkom.domain.admin.entity.QAdmin.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dokebi.dalkom.domain.admin.dto.AdminSearchCondition;
import com.dokebi.dalkom.domain.admin.dto.QReadAdminResponse;
import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class AdminRepositoryImpl implements AdminRepositoryCustom {
	private final JPAQueryFactory queryFactory;
	
	public AdminRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<ReadAdminResponse> searchAdmin(AdminSearchCondition condition, Pageable pageable) {
		List<ReadAdminResponse> content = queryFactory
			.select(new QReadAdminResponse(
				admin.adminSeq,
				admin.adminId,
				admin.role,
				admin.nickname,
				admin.name,
				admin.depart

			))
			.from(admin)
			.where(
				adminIdEq(condition.getAdminId()),
				nicknameEq(condition.getNickname()),
				nameEq(condition.getName()),
				departEq(condition.getDepart())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Admin> countQuery = queryFactory
			.select(admin)
			.from(admin)
			.where(
				adminIdEq(condition.getAdminId()),
				nicknameEq(condition.getNickname()),
				nicknameEq(condition.getName()),
				departEq(condition.getDepart())
			);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private BooleanExpression adminIdEq(String adminId) {
		return hasText(adminId) ? admin.adminId.contains(adminId) : null;
	}

	private BooleanExpression nameEq(String name) {
		return hasText(name) ? admin.name.contains(name) : null;
	}

	private BooleanExpression departEq(String depart) {
		return hasText(depart) ? admin.depart.contains(depart) : null;
	}

	private BooleanExpression nicknameEq(String nickname) {
		return hasText(nickname) ? admin.nickname.contains(nickname) : null;
	}
}
