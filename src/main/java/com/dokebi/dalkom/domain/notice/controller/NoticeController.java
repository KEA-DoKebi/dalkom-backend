package com.dokebi.dalkom.domain.notice.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;
import com.dokebi.dalkom.domain.notice.service.NoticeService;
import com.dokebi.dalkom.domain.user.config.LoginUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class NoticeController {
	private final NoticeService noticeService;

	// NOTICE-001 (특정 공지 조회)
	@GetMapping("/api/notice/{noticeSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response readNotice(@PathVariable Long noticeSeq) {
		return Response.success(noticeService.readNotice(noticeSeq));
	}

	// NOTICE-002 (특정 공지 삭제)
	@DeleteMapping("/api/notice/{noticeSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response deleteNotice(@PathVariable Long noticeSeq) {
		noticeService.deleteNotice(noticeSeq);
		return Response.success();
	}

	// NOTICE-003 (특정 공지 수정)
	@PutMapping("/api/notice/{noticeSeq}")
	@ResponseStatus(HttpStatus.OK)
	public Response updateNotice(@PathVariable Long noticeSeq, @LoginUser Long adminSeq,
		@Valid @RequestBody NoticeUpdateRequest request) {
		noticeService.updateNotice(noticeSeq, adminSeq, request);
		return Response.success();
	}

	// NOTICE-004 (공지 작성)
	@PostMapping("/api/notice")
	@ResponseStatus(HttpStatus.OK)
	public Response createNotice(@LoginUser Long adminSeq, @Valid @RequestBody NoticeCreateRequest request) {
		noticeService.createNotice(adminSeq, request);
		return Response.success();
	}

	// NOTICE-005 (공지 전체 조회)
	@GetMapping("/api/notice")
	@ResponseStatus(HttpStatus.OK)
	public Response readNoticeList(Pageable pageable) {
		return Response.success(noticeService.readNoticeList(pageable));
	}

	// NOTICE-006 (공지 검색)
	@GetMapping("/api/notice/search")
	@ResponseStatus(HttpStatus.OK)
	public Response readNoticeListBySearch(@RequestParam(required = false) String nickname,
		@RequestParam(required = false) String title, Pageable pageable) {
		return Response.success(noticeService.readNoticeListBySearch(nickname, title, pageable));
	}
}
