package com.devpass.global.payload;

import org.springframework.http.HttpStatus;

import com.devpass.global.payload.apicode.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"httpStatus", "code", "message", "result"})
public class ApiResponse<T> {

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@JsonInclude(Include.NON_NULL)
	private final T result;

	public static <T> ApiResponse<T> of(ResponseCode status, T result) {
		return new ApiResponse<>(status.getReason().getHttpStatus(),
			status.getReason().getCode(),
			status.getReason().getMessage(),
			result);
	}

	public static <T> ApiResponse<T> of(ResponseCode status) {
		return new ApiResponse<>(status.getReason().getHttpStatus(),
			status.getReason().getCode(),
			status.getReason().getMessage(),
			null);
	}
}
