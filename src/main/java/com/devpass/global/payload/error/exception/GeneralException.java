package com.devpass.global.payload.error.exception;

import java.util.ArrayList;
import java.util.List;

import com.devpass.global.payload.apicode.ErrorStatus;
import com.devpass.global.payload.error.ErrorField;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

	private ErrorStatus errorCode;
	private List<ErrorField> errors = new ArrayList<>();

	public GeneralException(ErrorStatus errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
