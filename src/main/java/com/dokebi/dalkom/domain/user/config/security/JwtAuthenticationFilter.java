package com.dokebi.dalkom.domain.user.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.dokebi.dalkom.domain.user.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
	private final TokenService tokenService;
	private final CustomUserService userService;

	//필터에 의해 doFilter 자동 실행
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		//요청 헤더의 AccessToken 가져오기
		String token = ((HttpServletRequest)request).getHeader("AccessToken");

		System.out.println("====token from header====");
		System.out.println(token);

		if (token != null) {
			try {
				//AccessToken 복호화
				String userSeq = decryptAccessToken(token);

				if (userSeq == null) {
					handleRefreshTokenExpired(request, response);
					return;
				}

				//요청 헤더에 userSeq 세팅
				((HttpServletRequest)request).setAttribute("userSeq", userSeq);

				//인증
				setAuthentication(userSeq);
			} catch (Exception e) {
				log.error("Error processing token", e);
			}

		}
		//다음 필터 진행
		chain.doFilter(request, response);

	}

	private String decryptAccessToken(String accessToken) {
		return tokenService.decryptAccessToken(accessToken);
	}

	private void setAuthentication(String userSeq) {
		CustomUserDetails userDetails = userService.loadUserByUsername(userSeq);
		//userSeq, 권한 넣어줌
		SecurityContextHolder.getContext()
			.setAuthentication(new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
	}

	private void handleRefreshTokenExpired(ServletRequest request, ServletResponse response) throws IOException {
		// refresh token이 만료되었을 때의 처리를 수행
		// 클라이언트에게 로그아웃 요청이나 새로운 인증을 받을 수 있도록 안내
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token expired");
	}

}
