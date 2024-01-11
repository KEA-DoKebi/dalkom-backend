package com.dokebi.dalkom.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dokebi.dalkom.domain.user.entitiy.User;

public interface UserRepository extends JpaRepository<User,Long> {
	// boolean existsByNickname(String nickname);
	//
	// @Modifying
	// @Query("UPDATE User u SET u.password = :password, u.nickname = :nickname, u.profileImgSeq = :profileImgSeq WHERE u.userSeq = :userSeq")
	// void updateByUserSeq(Long userSeq, String password, String nickname,String profileImgSeq);
}