package com.dokebi.dalkom.domain.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.user.entity.User;

import io.lettuce.core.dynamic.annotation.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserSeq(Long userSeq);

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	@Modifying
	@Query("UPDATE User u SET u.password = :password, u.nickname = :nickname, u.address = :address WHERE u.userSeq = :userSeq")
	void updateUserWithPassword(Long userSeq, String password, String nickname, String address);

	@Modifying
	@Query("UPDATE User u SET u.nickname = :nickname, u.address = :address WHERE u.userSeq = :userSeq")
	void updateUserWithoutPassword(Long userSeq, String nickname, String address);

	@Query("SELECT u FROM User u " +
		"WHERE (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) " +
		"AND (:nickname IS NULL OR u.nickname LIKE CONCAT('%', :nickname, '%'))")
	Page<User> findUsersBySearch(@Param("email") String email, @Param("nickname") String nickname,
		Pageable pageable);
}
