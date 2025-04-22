package com.devpass.global.payload.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@JsonPropertyOrder({"timestamp", "httpStatus", "code", "message", "errors", "path"})
public class ErrorResponse {

	private LocalDateTime timestamp;
	private HttpStatus httpStatus;
	private String code;
	private String errorMessage;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorField> errors;

	private String path;

	private ErrorResponse(ErrorStatus errorCode, List<ErrorField> fieldErrors, String path) {
		this.timestamp = LocalDateTime.now();
		this.httpStatus = errorCode.getHttpStatus();
		this.code = errorCode.getCode();
		this.errorMessage = errorCode.getMessage();
		this.errors = fieldErrors.isEmpty() ? null : fieldErrors;
		this.path = path;
	}

	private ErrorResponse(ErrorStatus errorCode, String errorMessage, String field, String path) {
		this.timestamp = LocalDateTime.now();
		this.httpStatus = errorCode.getHttpStatus();
		this.code = errorCode.getCode();
		this.errorMessage = field == null ? errorMessage : null;
		this.errors = field == null ? null : ErrorField.of(field, "", errorMessage);
		this.path = path;
	}

	public static ErrorResponse of(ErrorStatus errorCode, List<ErrorField> fieldErrors, String path) {
		return new ErrorResponse(errorCode, fieldErrors, path);
	}

	public static ErrorResponse of(ErrorStatus errorCode, String path) {
		return new ErrorResponse(errorCode, new ArrayList<>(), path);
	}

	public static ErrorResponse of(ErrorStatus errorCode, Set<ConstraintViolation<?>> violations, String path) {
		List<ErrorField> fieldErrors = ErrorField.of(violations);
		return new ErrorResponse(errorCode, fieldErrors, path);
	}

	public static ErrorResponse of(ErrorStatus errorCode, BindingResult bindingResult, String path) {
		List<ErrorField> fieldErrors = ErrorField.of(bindingResult);
		return new ErrorResponse(errorCode, fieldErrors, path);
	}

	public static ErrorResponse of(ErrorStatus errorCode, String errorMessage, String field, String path) {
		if (field == null) {
			return new ErrorResponse(errorCode, errorMessage, null, path);
		} else {
			return new ErrorResponse(errorCode, null, field, path);
		}
	}
}