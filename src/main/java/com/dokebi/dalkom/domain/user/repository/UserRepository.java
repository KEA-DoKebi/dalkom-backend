package com.dokebi.dalkom.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokebi.dalkom.domain.user.entity.User;

 
 
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserSeq(Long userSeq);

	User findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);
 
	// boolean existsByNickname(String nickname);

	//
	// @Modifying
	// @Query("UPDATE User u SET u.password = :password, u.nickname = :nickname, u.profileImgSeq = :profileImgSeq WHERE u.userSeq = :userSeq")
	// void updateByUserSeq(Long userSeq, String password, String nickname,String profileImgSeq);
}
