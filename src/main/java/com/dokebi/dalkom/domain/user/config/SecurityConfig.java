package com.dokebi.dalkom.domain.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dokebi.dalkom.domain.user.config.security.CustomUserService;
import com.dokebi.dalkom.domain.user.config.security.JwtAuthenticationFilter;
import com.dokebi.dalkom.domain.user.service.TokenService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenService tokenService;
	private final CustomUserService userService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/api/user/login");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable()
			.formLogin().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			//.anyRequest().authenticated()            // 나머지 모든 요청은  인증이 필요
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(tokenService, userService),
				UsernamePasswordAuthenticationFilter.class)
			.cors();

	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
