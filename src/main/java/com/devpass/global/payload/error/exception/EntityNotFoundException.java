package com.devpass.global.payload.error.exception;

import com.devpass.global.payload.apicode.ErrorCode;

public class EntityNotFoundException extends GeneralException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}