package com.dokebi.dalkom.domain.admin.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.dokebi.dalkom.domain.admin.dto.ReadAdminResponse;
import com.dokebi.dalkom.domain.admin.entity.Admin;
import com.dokebi.dalkom.domain.admin.exception.AdminNotFoundException;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminRepositoryTest {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	@DisplayName("existsByNickname 테스트")
	void existsByNicknameTest() {
		// Given
		String nickname = "Nickname9999";
		Admin admin = new Admin("admin9999", "123456a!", nickname, "사장9999", "IT");

		entityManager.persist(admin);
		entityManager.flush();

		// When
		boolean exists = adminRepository.existsByNickname(nickname);

		// Then
		assertTrue(exists);
	}

	@Test
	@DisplayName("findByAdminId 테스트")
	void findByAdminIdTest() {
		// Given
		String adminId = "adminId9999";
		Admin admin = new Admin(adminId, "123456a!", "nick", "사장9999", "IT");

		entityManager.persist(admin);
		entityManager.flush();

		// When
		Admin result = adminRepository.findByAdminId(adminId).orElseThrow(AdminNotFoundException::new);

		// Then
		assertEquals(admin, result);
	}

	@Test
	@DisplayName("findByAdminSeq 테스트")
	void findByAdminSeqTest() {
		// Given
		Long existingAdminSeq = 1L;

		// When
		Optional<Admin> optionalAdmin = adminRepository.findByAdminSeq(existingAdminSeq);

		// Then
		// DB에 userSeq가 1인 유저가 반드시 존재해야 함
		assertTrue(optionalAdmin.isPresent());
		Admin admin = optionalAdmin.get();
		assertEquals(existingAdminSeq, admin.getAdminSeq());
	}

	@Test
	@DisplayName("findAllAdminList 테스트")
	void findAllAdminListTest() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);

		// When
		Page<ReadAdminResponse> adminListPage = adminRepository.findAllAdminList(pageable);

		// Then
		assertNotNull(adminListPage); // 페이지 객체가 null이 아니어야 함
		assertFalse(adminListPage.isEmpty()); // 페이지가 비어 있지 않아야 함
		assertEquals(10, adminListPage.getSize()); // 페이지 크기가 10이어야 함 (페이지 크기 설정에 따라 다를 수 있음)
		// 페이지 내 첫 번째 Admin 객체의 adminSeq가 1인지 확인 (페이지 번호가 0이므로 첫 번째 항목)
		assertEquals(1L, adminListPage.getContent().get(0).getAdminSeq());
	}

	@Test
	@DisplayName("findAdminListByName 테스트")
	void findAdminListByNameTest() {
		// Given
		String name = "개발자";
		Pageable pageable = PageRequest.of(0, 3);

		// When
		Page<ReadAdminResponse> adminListPage = adminRepository.findAdminListByName(name, pageable);

		// Then
		assertNotNull(adminListPage);
		assertFalse(adminListPage.isEmpty());
	}

	@Test
	@DisplayName("findAdminListByAdminId 테스트")
	void findAdminListByAdminIdTest() {
		// Given
		String adminId = "admin";
		Pageable pageable = PageRequest.of(0, 7);

		// When
		Page<ReadAdminResponse> adminListPage = adminRepository.findAdminListByAdminId(adminId, pageable);

		// Then
		assertNotNull(adminListPage);
		assertFalse(adminListPage.isEmpty());
	}

	@Test
	@DisplayName("findAdminListByDepart 테스트")
	void findAdminListByDepartTest() {
		// Given
		String depart = "개발";
		Pageable pageable = PageRequest.of(0, 7);

		// When
		Page<ReadAdminResponse> adminListPage = adminRepository.findAdminListByDepart(depart, pageable);

		// Then
		assertNotNull(adminListPage);
		assertFalse(adminListPage.isEmpty());
	}

	@Test
	@DisplayName("findAdminListByNickname 테스트")
	void findAdminListByNickname() {
		// Given
		String nickname = "관리자";
		Pageable pageable = PageRequest.of(0, 7);

		// When
		Page<ReadAdminResponse> adminListPage = adminRepository.findAdminListByNickname(nickname, pageable);

		// Then
		assertNotNull(adminListPage);
		assertFalse(adminListPage.isEmpty());
	}
}
