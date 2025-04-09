package com.devpass.global.payload.error;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ErrorField {
	private String field;
	private String rejectedValue;
	private String reason;

	public ErrorField(String field, String rejectedValue, String reason) {
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.reason = reason;
	}

	/**
	 * BindingResult에서 필드 오류 추출
	 */
	public static List<ErrorField> of(BindingResult bindingResult) {
		return bindingResult.getFieldErrors().stream()
			.map(error -> new ErrorField(
				error.getField(),
				error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
				error.getDefaultMessage()))
			.collect(Collectors.toList());
	}

	/**
	 * ConstraintViolation에서 필드 오류 추출
	 */
	public static List<ErrorField> of(Set<ConstraintViolation<?>> violations) {
		return violations.stream()
			.map(violation -> {
				String fieldPath = violation.getPropertyPath().toString();
				return new ErrorField(
					fieldPath,
					violation.getInvalidValue() == null ? "" : violation.getInvalidValue().toString(),
					violation.getMessage());
			})
			.collect(Collectors.toList());
	}

	/**
	 * 단일 필드 오류 리스트 생성
	 */
	public static List<ErrorField> of(String field, String rejectedValue, String reason) {
		List<ErrorField> fieldErrors = new ArrayList<>();
		fieldErrors.add(new ErrorField(field, rejectedValue, reason));
		return fieldErrors;
	}
}