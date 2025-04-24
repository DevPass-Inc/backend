package com.devpass.global.oauth.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

public class ErrorResponseUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void sendErrorResponse(HttpServletResponse response, ErrorStatus errorStatus) throws
		IOException {
		response.setStatus(errorStatus.getHttpStatus().value());
		response.setContentType("application/json;charset=UTF-8");

		Map<String, Object> errorResponse = new LinkedHashMap<>();
		errorResponse.put("code", errorStatus.getCode());
		errorResponse.put("message", errorStatus.getMessage());

		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
