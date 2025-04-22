package com.devpass.global.oauth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.devpass.global.oauth.util.ErrorResponseUtil;
import com.devpass.global.payload.apicode.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		ErrorResponseUtil.sendErrorResponse(response, ErrorStatus.UNAUTHORIZED);
	}
}
