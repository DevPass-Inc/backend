package com.devpass.backend.global.common.response;

import com.devpass.backend.global.common.status.BaseStatus;
import com.devpass.backend.global.error.ErrorCode;
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
        return new CustomResponse<>(status.getReason().getStatus(),
                status.getReason().getCode(),
                status.getReason().getMessage(),
                result);
    }
    public static <T> CustomResponse<T> of(BaseStatus status){
        return new CustomResponse<>(status.getReason().getStatus(),
                status.getReason().getCode(),
                status.getReason().getMessage(),
                null);
    }

    public static <T> CustomResponse<T> ok(BaseStatus status){
        return new CustomResponse<>(status.getReason().getStatus(),
                status.getReason().getCode(),
                status.getReason().getMessage(),
                null);
    }

    public CustomResponse(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.result = null;
    }

    public static <T> CustomResponse<T> of(ErrorCode errorCode) {
        return new CustomResponse<>(
                errorCode.getHttpStatus(),
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}
