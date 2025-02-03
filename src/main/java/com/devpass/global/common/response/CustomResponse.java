package com.devpass.global.common.response;

import com.devpass.global.common.status.BaseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"httpStatus", "code", "message", "result"})
public class CustomResponse<T> {

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @JsonInclude(Include.NON_NULL)
    private final T result;

    public static <T> CustomResponse<T> of(BaseStatus status, T result){

}
