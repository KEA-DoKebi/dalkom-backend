package com.dokebi.dalkom.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserSeq(Long userSeq);

	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	@Modifying
	@Query("UPDATE User u SET u.password = :password, u.nickname = :nickname, u.address = :address WHERE u.userSeq = :userSeq")
	void updateUserWithPassword(Long userSeq, String password, String nickname, String address);

	@Modifying
	@Query("UPDATE User u SET u.nickname = :nickname, u.address = :address WHERE u.userSeq = :userSeq")
	void updateUserWithoutPassword(Long userSeq, String nickname, String address);
}
