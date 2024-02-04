package com.dokebi.dalkom.domain.notice.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;
import com.dokebi.dalkom.domain.notice.entity.Notice;
import com.dokebi.dalkom.domain.notice.exception.NoticeNotFoundException;
import com.dokebi.dalkom.domain.notice.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

	private final NoticeRepository noticeRepository;
	private final AdminService adminService;

	@Transactional
	public NoticeOneResponse readNotice(Long noticeSeq) {

		return noticeRepository.findNotice(noticeSeq);
	}

	@Transactional
	public Page<NoticeListResponse> readNoticeList(Pageable pageable) {

		return noticeRepository.findNoticeList(pageable);
	}

	@Transactional
	public void createNotice(Long adminSeq, NoticeCreateRequest request) {

		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		Notice notice = new Notice(admin, request.getTitle(), request.getContent(), request.getState());
		noticeRepository.save(notice);
	}

	@Transactional
	public void updateNotice(Long noticeSeq, Long adminSeq, NoticeUpdateRequest request) {

		Notice notice = noticeRepository.findByNoticeSeq(noticeSeq);
		notice.setTitle(request.getTitle());
		notice.setContent(request.getContent());
		Admin admin = adminService.readAdminByAdminSeq(adminSeq);
		notice.setAdmin(admin);
		notice.setState(request.getState());
		noticeRepository.save(notice);
	}

	@Transactional
	public void deleteNotice(Long noticeSeq) {
		try {
			noticeRepository.deleteById(noticeSeq);
		} catch (EmptyResultDataAccessException e) {
			throw new NoticeNotFoundException();
		}
	}

	@Transactional
	public Page<NoticeListResponse> readNoticeListBySearch(String nickName, String title, Pageable pageable) {
		if (nickName != null) {
			return noticeRepository.findNoticeListByNickname(nickName, pageable);
		} else if (title != null) {
			return noticeRepository.findNoticeListByTitle(title, pageable);
		} else {
			return noticeRepository.findNoticeList(pageable);
		}
	}

}
