package com.dokebi.dalkom.domain.notice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.service.AdminService;
import com.dokebi.dalkom.domain.notice.dto.NoticeCreateRequest;
import com.dokebi.dalkom.domain.notice.dto.NoticeListResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeOneResponse;
import com.dokebi.dalkom.domain.notice.dto.NoticeUpdateRequest;
import com.dokebi.dalkom.domain.notice.entity.Notice;
import com.dokebi.dalkom.domain.notice.factory.NoticeCreateRequestFactory;
import com.dokebi.dalkom.domain.notice.factory.NoticeListResponseFactory;
import com.dokebi.dalkom.domain.notice.factory.NoticeOneResponseFactory;
import com.dokebi.dalkom.domain.notice.factory.NoticeUpdateRequestFactory;
import com.dokebi.dalkom.domain.notice.repository.NoticeRepository;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {

	@InjectMocks
	private NoticeService noticeService;

	@Mock
	private NoticeRepository noticeRepository;

	@Mock
	private AdminService adminService;

	@Captor
	private ArgumentCaptor<Notice> noticeArgumentCaptor;

	@Test
	void createNoticeTest() {
		// Given
		Long adminSeq = 1L;
		NoticeCreateRequest request = NoticeCreateRequestFactory.createNoticeCreateRequest();

		Admin admin = new Admin("adminId", "password", "nickname", "name", "depart");

		when(adminService.readAdminByAdminSeq(adminSeq)).thenReturn(admin);

		// When
		noticeService.createNotice(adminSeq, request);

		// Then
		verify(noticeRepository).save(noticeArgumentCaptor.capture());
		Notice capturedNotice = noticeArgumentCaptor.getValue();

		assertEquals(admin, capturedNotice.getAdmin());
		assertEquals(request.getTitle(), capturedNotice.getTitle());
		assertEquals(request.getContent(), capturedNotice.getContent());
		assertEquals(request.getState(), capturedNotice.getState());
	}

	@Test
	void readNoticeTest() {
		// Given
		Long noticeSeq = 1L;
		NoticeOneResponse fakeResponse = NoticeOneResponseFactory.createNoticeOneResponse();

		when(noticeService.readNotice(noticeSeq)).thenReturn(fakeResponse);

		// When
		NoticeOneResponse noticeOneResponse = noticeService.readNotice(noticeSeq);

		// Then
		assertEquals(fakeResponse, noticeOneResponse);
	}

	@Test
	void readNoticeListTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 3);

		NoticeListResponse fakeResponse1 = NoticeListResponseFactory.createNoticeListResponse();
		NoticeListResponse fakeResponse2 = NoticeListResponseFactory.createNoticeListResponse();
		NoticeListResponse fakeResponse3 = NoticeListResponseFactory.createNoticeListResponse();

		List<NoticeListResponse> responseList = Arrays.asList(fakeResponse1, fakeResponse2, fakeResponse3);
		Page<NoticeListResponse> responsePage = new PageImpl<>(responseList, pageable, responseList.size());

		when(noticeService.readNoticeList(pageable)).thenReturn(responsePage);

		// When
		Page<NoticeListResponse> noticeListResponseListPage = noticeService.readNoticeList(pageable);

		// Then
		assertEquals(responseList.size(), noticeListResponseListPage.getContent().size());
		assertEquals(responseList, noticeListResponseListPage.getContent());
	}

	@Test
	void updateNoticeTest() {
		// Given
		Admin admin = new Admin("adminId", "password", "nickname", "name", "depart");
		Notice notice = new Notice(admin, "title", "content", "Y");

		NoticeUpdateRequest request = NoticeUpdateRequestFactory.createNoticeUpdateRequest();

		Long noticeSeq = 1L;
		Long adminSeq = 1L;
		when(adminService.readAdminByAdminSeq(adminSeq)).thenReturn(admin);
		when(noticeRepository.findByNoticeSeq(noticeSeq)).thenReturn(notice);

		// When
		noticeService.updateNotice(noticeSeq, adminSeq, request);

		// Then
		verify(noticeRepository).findByNoticeSeq(noticeSeq);
		verify(adminService).readAdminByAdminSeq(adminSeq);
		verify(noticeRepository).save(any(Notice.class));
		assertEquals("updatedTitle", notice.getTitle());
		assertEquals("updatedContent", notice.getContent());
		assertEquals("N", notice.getState());
	}

	// @Test
	// @DisplayName("공지 삭제 - 성공")
	// void deleteNoticeSuccessTest() {
	// 	// Given
	// 	Long noticeSeq = 1L;
	// 	Admin admin = new Admin("adminId", "password", "nickname", "name", "depart");
	// 	Notice notice = new Notice(admin, "title", "content", "Y");
	//
	// 	when(noticeRepository.findById(noticeSeq)).thenReturn(Optional.of(notice));
	//
	// 	// When
	// 	noticeService.deleteNotice(noticeSeq);
	//
	// 	// Then
	// 	verify(noticeRepository).deleteById(noticeSeq);
	// }
	//
	// @Test
	// @DisplayName("공지 삭제 - 실패(공지를 찾을 수 없음)")
	// void deleteNoticeFailTest() {
	// 	// Given
	// 	Long noticeSeq = 1L;
	//
	// 	when(noticeRepository.findById(noticeSeq)).thenReturn(Optional.empty());
	//
	// 	// When
	// 	assertThrows(NoticeNotFoundException.class, () -> noticeService.deleteNotice(noticeSeq));
	//
	// 	// Then
	// 	verify(noticeRepository, never()).deleteById(anyLong());
	// }
}