package com.devpass.global.payload.apicode;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode implements ResponseCode {

	OK(HttpStatus.OK, "COMMON_200", "성공적으로 처리되었습니다."),
	CREATED(HttpStatus.CREATED, "COMMON_201", "성공적으로 생성되었습니다."),
	NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON_204", "성공적으로 삭제되었습니다."),
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ReasonDTO getReason() {
		return ReasonDTO.builder()
			.status(httpStatus)
			.code(code)
			.message(message)
			.build();
	}
}
