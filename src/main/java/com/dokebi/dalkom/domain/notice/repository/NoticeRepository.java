package com.dokebi.dalkom.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	NoticeOneResponse findNotice(@Param("noticeSeq") Long noticeSeq);

	@Query("SELECT NEW com.dokebi.dalkom.domain.notice.dto.NoticeListResponse("
		+ "n.noticeSeq, n.title, n.content, n.createdAt, n.modifiedAt, n.admin.nickname, n.state) " +
		"FROM Notice n " +
		"ORDER BY n.state DESC, n.createdAt ASC")
	Page<NoticeListResponse> findNoticeList(Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.notice.dto.NoticeListResponse("
		+ "n.noticeSeq, n.title, n.content, n.createdAt, n.modifiedAt, n.admin.nickname, n.state) " +
		"FROM Notice n WHERE n.admin.nickname LIKE CONCAT('%', :nickname, '%') ")
	Page<NoticeListResponse> findNoticeListByNickname(@Param("nickname") String nickname,
		Pageable pageable);

	@Query("SELECT NEW com.dokebi.dalkom.domain.notice.dto.NoticeListResponse("
		+ "n.noticeSeq, n.title, n.content, n.createdAt, n.modifiedAt, n.admin.nickname, n.state) " +
		"FROM Notice n WHERE n.title LIKE CONCAT('%', :title, '%')")
	Page<NoticeListResponse> findNoticeListByTitle(@Param("title") String title, Pageable pageable);
}
