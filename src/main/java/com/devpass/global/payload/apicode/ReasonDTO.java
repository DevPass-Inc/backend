package com.devpass.global.payload.apicode;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDTO {
	private HttpStatus httpStatus;
	private String code;
	private String message;
}
