package com.dokebi.dalkom.domain.notice.repository;

import static com.dokebi.dalkom.domain.notice.entity.QNotice.*;
import static org.springframework.util.StringUtils.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeSearchCondition;
import com.dokebi.dalkom.domain.notice.dto.QNoticeListResponse;
import com.dokebi.dalkom.domain.notice.entity.Notice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class NoticeRepositoryImpl implements NoticeRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	public NoticeRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<NoticeListResponse> searchNotice(NoticeSearchCondition condition, Pageable pageable) {
		List<NoticeListResponse> content = queryFactory
			.select(new QNoticeListResponse(
				notice.noticeSeq,
				notice.title,
				notice.content,
				notice.createdAt,
				notice.modifiedAt,
				notice.admin.nickname,
				notice.state
			))
			.from(notice)
			.leftJoin(notice.admin)
			.where(
				nicknameEq(condition.getNickname()),
				titleEq(condition.getTitle())
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Notice> countQuery = queryFactory
			.select(notice)
			.from(notice)
			.where(
				nicknameEq(condition.getNickname()),
				titleEq(condition.getTitle())
			);
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

	}

	private BooleanExpression titleEq(String title) {
		return hasText(title) ? notice.title.contains(title) : null;
	}

	private BooleanExpression nicknameEq(String nickname) {
		return hasText(nickname) ? notice.admin.nickname.contains(nickname) : null;
	}

}





