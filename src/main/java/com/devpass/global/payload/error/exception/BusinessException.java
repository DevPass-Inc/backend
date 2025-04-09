package com.devpass.global.payload.error.exception;

import java.util.ArrayList;
import java.util.List;

import com.devpass.global.payload.apicode.ErrorCode;
import com.devpass.global.payload.error.ErrorResponse.FieldError;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private ErrorCode errorCode;
	private List<FieldError> errors = new ArrayList<>();

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
