package com.devpass.global.payload.error.exception;

import com.devpass.global.payload.apicode.ErrorStatus;

public class EntityNotFoundException extends GeneralException {
	public EntityNotFoundException(ErrorStatus errorCode) {
		super(errorCode);
	}
}