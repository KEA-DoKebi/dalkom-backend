package com.dokebi.dalkom.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeSearchCondition;

public interface NoticeRepositoryCustom {
	Page<NoticeListResponse> searchNotice(NoticeSearchCondition condition, Pageable pageable);

}
