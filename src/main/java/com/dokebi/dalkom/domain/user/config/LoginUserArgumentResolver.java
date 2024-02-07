package com.dokebi.dalkom.domain.user.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	private final HttpServletRequest request;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
		boolean isUserClass = Long.class.equals(parameter.getParameterType());
		return isLoginUserAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (request.getAttribute("userSeq") == null)
			return Long.parseLong((String)request.getAttribute("adminSeq"));
		return Long.parseLong((String)request.getAttribute("userSeq"));
	}
}
