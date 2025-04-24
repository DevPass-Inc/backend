package com.devpass.global.oauth.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.devpass.global.oauth.util.ErrorResponseUtil;
import com.devpass.global.payload.apicode.ErrorStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException {

		ErrorResponseUtil.sendErrorResponse(response, ErrorStatus.INTERNAL_SERVER_ERROR);
	}
}