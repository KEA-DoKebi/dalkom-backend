package com.dokebi.dalkom.domain.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.repository.AdminRepository;
import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeModifyRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse;
import com.dokebi.dalkom.domain.notice.entity.Notice;
import com.dokebi.dalkom.domain.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final AdminRepository adminRepository;

	@Transactional
	public NoticeOneResponse getNotice(Long noticeSeq) {

		return noticeRepository.getNotice(noticeSeq);
	}

	@Transactional
	public List<NoticeListResponse> getNoticeList() {

		return noticeRepository.getNoticeList();
	}

	@Transactional
	public void createNotice(NoticeCreateRequest request) {
		Admin admin = adminRepository.findByAdminSeq(request.getAdminSeq());
		Notice notice = new Notice(admin, request.getTitle(), request.getContent(), request.getState());
		noticeRepository.save(notice);
	}

	public void modifyNotice(Long noticeSeq, NoticeModifyRequest request) {
		Notice notice = noticeRepository.findByNoticeSeq(noticeSeq);
		notice.setTitle(request.getTitle());
		notice.setContent(request.getContent());
		Admin admin = adminRepository.findByAdminSeq(request.getAdminSeq());
		notice.setAdmin(admin);
		notice.setState(request.getState());
		noticeRepository.save(notice);
	}

	@Transactional
	public void deleteNotice(Long noticeSeq) {
		Notice notice = noticeRepository.findByNoticeSeq(noticeSeq);
		noticeRepository.delete(notice);
	}
}
