package com.dokebi.dalkom.domain.user.config.security;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dokebi.dalkom.domain.user.entity.User;
import com.dokebi.dalkom.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {
	private final UserService userService;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.readUserByUserSeq(Long.parseLong(username));
		Set<GrantedAuthority> authorities = new HashSet<>(Collections.singleton(new SimpleGrantedAuthority("USER")));
		return new CustomUserDetails(String.valueOf(user.getUserSeq()), authorities);
	}

}
