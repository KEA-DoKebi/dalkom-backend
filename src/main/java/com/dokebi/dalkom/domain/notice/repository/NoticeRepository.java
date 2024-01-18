package com.dokebi.dalkom.domain.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse;
import com.dokebi.dalkom.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	Notice findByNoticeSeq(Long noticeSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse("
		+ " n.title, n.content, n.createdAt, n.admin.nickname, n.state) " +
		"FROM Notice n " +
		"WHERE n.noticeSeq = :noticeSeq")
	NoticeOneResponse readNotice(@Param("noticeSeq") Long noticeSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.notice.dto.NoticeListResponse("
		+ "n.noticeSeq, n.title, n.content, n.createdAt, n.modifiedAt, n.admin.nickname, n.state) " +
		"FROM Notice n ")
	List<NoticeListResponse> readNoticeList();
}
